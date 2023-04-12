package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.utils.StringUtils;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.domain.request.GoodsRequest;
import com.retail.shop.mapper.SkuMapper;
import com.retail.shop.service.SkuService;
import com.retail.shop.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("skuService")
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Override
    public List<GoodsVo> goodsList(GoodsRequest request) {
        int i = 0;
        List<GoodsVo> list = skuMapper.goodsList();
        ArrayList<GoodsVo> arrayList = new ArrayList<>();
        //根据商品标题搜索
        if (StringUtils.isNotBlank(request.getSkuTitle())) {
            i++;
            list.stream().filter(c->c.getSkuTitle().matches(".*"+request.getSkuTitle()+".*")).forEach(c->arrayList.add(c));
        }
        //根据品牌名称搜索
        if (StringUtils.isNotBlank(request.getBrandName())) {
            i++;
            list.stream().filter(c->c.getSkuTitle().matches(".*"+request.getBrandName()+".*")).forEach(c->arrayList.add(c));
        }
        if (i!=0){
            return arrayList;
        }
        return list;
    }
}
