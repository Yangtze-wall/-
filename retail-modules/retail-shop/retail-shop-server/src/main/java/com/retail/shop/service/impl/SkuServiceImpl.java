package com.retail.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.common.utils.StringUtils;
import com.retail.shop.domain.*;
import com.retail.common.domain.vo.ProductVo;
import com.retail.shop.domain.vo.SearchParam;
import com.retail.shop.domain.vo.SkuModelVo;
import com.retail.shop.mapper.BrandMapper;
import com.retail.shop.mapper.InventoryMapper;
import com.retail.shop.mapper.SkuMapper;
import com.retail.shop.mapper.SpuMapper;
import com.retail.shop.service.*;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("skuService")
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    @Autowired
    private SkuMapper skuMapper;
    RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("139.196.95.132", 9200, "http")));

    private static String INDEX_NAME="product";

    @Autowired
    private ClassifiedService classifiedService;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SpuMapper spuMapper;
     @Autowired
    private SpuService spuService;

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandMapper brandMapper;


    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<SkuModelVo> findAll() {
        //展示spu上架状态
        List<SkuEntity> list = baseMapper.selectList(new QueryWrapper<SkuEntity>());
        //循环 map转换
        List<SkuModelVo> collect = list.stream().map(c -> {
            SkuModelVo skuModelVo = new SkuModelVo();
            skuModelVo.setSkuId(c.getId());
            skuModelVo.setSkuTitle(c.getSkuTitle());
            skuModelVo.setSkuPrice(c.getSkuPrice());
            skuModelVo.setSkuImage(c.getSkuImage());
            skuModelVo.setSpuId(c.getSpuId());
            skuModelVo.setSkuSell(c.getSkuSell());
            //库存
//            InventoryEntity inventoryEntity = inventoryService.getById(c.getSpuId());
            InventoryEntity inventoryEntity = inventoryService.getOne(new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId, c.getSpuId()));
            skuModelVo.setInventoryCount(inventoryEntity.getInventoryCount());
            //状态
            SpuEntity spuEntity = spuService.getOne(new QueryWrapper<SpuEntity>().lambda().eq(SpuEntity::getId, c.getSpuId()));
            skuModelVo.setSpuStatus(spuEntity.getSpuStatus());
            //设置分类
            ClassifiedEntity classifiedEntity = classifiedService.getById(c.getClassifiedId());
            skuModelVo.setClassifiedId(c.getClassifiedId());
            skuModelVo.setClassifiedName(classifiedEntity.getName());
            //设置品牌
            BrandEntity brandEntity = brandService.getById(c.getBrandId());
            skuModelVo.setBrandId(c.getBrandId());
            skuModelVo.setBrandName(brandEntity.getBrandName());
            skuModelVo.setBrandLogo(brandEntity.getBrandLogo());
            return skuModelVo;

        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Result<PageResult<SkuModelVo>> search(SearchParam searchParam) {
        try {
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNotEmpty(searchParam.getSkuTitle())){
                boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle",searchParam.getSkuTitle()));
            }
            if (searchParam.getBrandId()!=null){
                boolQueryBuilder.must(QueryBuilders.termQuery("brandId",searchParam.getBrandId()));
            }
            if (searchParam.getClassifiedId()!=null){
                boolQueryBuilder.must(QueryBuilders.termQuery("classifiedId",searchParam.getClassifiedId()));
            }
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.from((searchParam.getPageNum()-1)*searchParam.getPageSize());
            searchSourceBuilder.size(searchParam.getPageSize());

            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<span style=\"color: red\">");
            highlightBuilder.postTags("</span>");

            searchSourceBuilder.highlighter(highlightBuilder);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();

            long total = hits.getTotalHits().value;

            List<SkuModelVo> list = Arrays.stream(hits.getHits()).map(c -> {
                String sourceAsString = c.getSourceAsString();
                SkuModelVo skuModelVo = JSON.parseObject(sourceAsString, SkuModelVo.class);
                if (c.getHighlightFields().get("skuTitle") != null) {
                    String skuTitle = c.getHighlightFields().get("skuTitle").fragments()[0].string();
                    skuModelVo.setSkuTitle(skuTitle);
                }
                return skuModelVo;

            }).collect(Collectors.toList());

            return PageResult.toResult(total,list);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Result deleteSkuById(Long id) {
        baseMapper.deleteById(id);
        return Result.success();
    }



    @Override
    public Result insertSku(ProductVo productVo) {
        //添加商品 spu
        SpuEntity spuEntity = new SpuEntity();
        spuEntity.setSort(productVo.getSort());
        spuEntity.setBrandId(productVo.getBrandId());
        spuEntity.setClassifiedId(Long.valueOf(productVo.getClassifiedIds()[2]));
        spuEntity.setSpuLetter(productVo.getSpuLetter());
        spuEntity.setSpuName(productVo.getSpuName());
        spuEntity.setSpuCreateTime(new Date());
        spuEntity.setSpuStatus(1);
        spuEntity.setSpuUpdateTime(new Date());
        spuMapper.insert(spuEntity);
        Long spuId= spuEntity.getId();
        //添加库存
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setInventoryCount(productVo.getInventoryCount());
        inventoryEntity.setSpuId(spuId);
        inventoryEntity.setInventoryCreateTime(new Date());
        inventoryMapper.insert(inventoryEntity);

        SkuEntity skuEntity = new SkuEntity();
        skuEntity.setSpuId(spuId);
        skuEntity.setBrandId(productVo.getBrandId());
        skuEntity.setSkuImage(productVo.getSkuImage());
        skuEntity.setSkuSell(productVo.getSkuSell());
        skuEntity.setSkuTitle(productVo.getSkuTitle());
        skuEntity.setSkuPrice(productVo.getSkuPrice());
        skuEntity.setSkuSubhead(productVo.getSkuSubhead());
        skuEntity.setClassifiedId(Long.valueOf(productVo.getClassifiedIds()[2]));
        this.baseMapper.insert(skuEntity);

        return Result.success();
    }

    @Override
    public Result updateSku(ProductVo productVo) {
        //修改spu
        SpuEntity spuEntity = new SpuEntity();
        spuEntity.setSort(productVo.getSort());
        spuEntity.setBrandId(productVo.getBrandId());
        spuEntity.setClassifiedId(Long.valueOf(productVo.getClassifiedIds()[2]));
        spuEntity.setSpuLetter(productVo.getSpuLetter());
        spuEntity.setSpuName(productVo.getSpuName());
        spuEntity.setSpuUpdateTime(new Date());
        spuMapper.update(spuEntity,new UpdateWrapper<SpuEntity>().lambda().eq(SpuEntity::getId,productVo.getSpuId()));
        //修改库存
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setInventoryCount(productVo.getInventoryCount());
        inventoryMapper.update(inventoryEntity,new UpdateWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId,productVo.getSpuId()));

        SkuEntity skuEntity = new SkuEntity();
        skuEntity.setBrandId(productVo.getBrandId());
        skuEntity.setSkuImage(productVo.getSkuImage());
        skuEntity.setSkuSell(productVo.getSkuSell());
        skuEntity.setSkuTitle(productVo.getSkuTitle());
        skuEntity.setSkuPrice(productVo.getSkuPrice());
        skuEntity.setSkuSubhead(productVo.getSkuSubhead());
        skuEntity.setClassifiedId(Long.valueOf(productVo.getClassifiedIds()[2]));
        this.baseMapper.update(skuEntity,new UpdateWrapper<SkuEntity>().lambda().eq(SkuEntity::getId,productVo.getSkuId()));
        return Result.success();
    }

    @Override
    public Result<ProductVo> findBySkuId(Long id) {
        SkuEntity skuEntity = this.baseMapper.selectById(id);
        ProductVo productVo = new ProductVo();
        productVo.setSkuId(id);
        productVo.setBrandId(skuEntity.getBrandId());
        productVo.setClassifiedId(skuEntity.getClassifiedId());
        productVo.setSkuImage(skuEntity.getSkuImage());
        productVo.setSkuPrice(skuEntity.getSkuPrice());
        productVo.setSkuSell(skuEntity.getSkuSell());
        productVo.setSkuSubhead(skuEntity.getSkuSubhead());
        productVo.setSkuTitle(skuEntity.getSkuTitle());

        Long spuId = skuEntity.getSpuId();
        SpuEntity spuEntity = spuMapper.selectById(spuId);
        productVo.setSort(spuEntity.getSort());
        productVo.setSpuId(skuEntity.getSpuId());
        productVo.setSpuLetter(spuEntity.getSpuLetter());
        productVo.setSpuName(spuEntity.getSpuName());
        InventoryEntity inventoryEntity = inventoryMapper.selectOne(new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId, spuId));

        productVo.setInventoryCount(inventoryEntity.getInventoryCount());

        return Result.success(productVo);
    }

    @Override
    public List<ProductVo> findSkuList() {
        //
        List<SkuEntity> list = baseMapper.selectList(new QueryWrapper<SkuEntity>());
        //循环 map转换
        List<ProductVo> collect = list.stream().map(c -> {
            ProductVo productVo = new ProductVo();
            productVo.setSkuId(c.getId());
            productVo.setSkuTitle(c.getSkuTitle());
            productVo.setSkuPrice(c.getSkuPrice());
            productVo.setSkuImage(c.getSkuImage());
            productVo.setSpuId(c.getSpuId());
            productVo.setSkuSell(c.getSkuSell());
            productVo.setSkuSubhead(c.getSkuSubhead());

            //库存
//            InventoryEntity inventoryEntity = inventoryService.getById(c.getSpuId());
            InventoryEntity inventoryEntity = inventoryService.getOne(new QueryWrapper<InventoryEntity>().lambda().eq(InventoryEntity::getSpuId, c.getSpuId()));
            productVo.setInventoryCount(inventoryEntity.getInventoryCount());
            //状态
            SpuEntity spuEntity = spuService.getOne(new QueryWrapper<SpuEntity>().lambda().eq(SpuEntity::getId, c.getSpuId()));
            productVo.setSpuStatus(spuEntity.getSpuStatus());
            productVo.setSpuName(spuEntity.getSpuName());
            productVo.setSpuLetter(spuEntity.getSpuLetter());
            productVo.setSort(spuEntity.getSort());
            //设置分类
            ClassifiedEntity classifiedEntity = classifiedService.getById(c.getClassifiedId());
            productVo.setClassifiedId(c.getClassifiedId());
            productVo.setClassifiedName(classifiedEntity.getName());
            //设置品牌
            BrandEntity brandEntity = brandService.getById(c.getBrandId());
            productVo.setBrandId(c.getBrandId());
            productVo.setBrandName(brandEntity.getBrandName());
            productVo.setBrandLogo(brandEntity.getBrandLogo());
            return productVo;

        }).collect(Collectors.toList());

        return collect;

    }

}
