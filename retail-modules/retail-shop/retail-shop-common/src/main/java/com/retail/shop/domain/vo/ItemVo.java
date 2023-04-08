package com.retail.shop.domain.vo;

import com.retail.shop.domain.SkuEntity;
import com.retail.shop.domain.SkuImagesEntity;
import com.retail.shop.domain.SpuDescEntity;
import lombok.Data;

import java.util.List;


@Data
public class ItemVo {


    //sku商品信息
    private SkuEntity skuEntity;
    //sku 图片信息
    private List<SkuImagesEntity> skuImagesEntityList;
    //属性规格信息
    private List<SkuAttrVo> attrVoList;
    //spu信息展示
    private SpuDescVo spuDescVo;



    @Data
    public static class SpuDescVo{
        private SpuDescEntity spuDescEntity;//里面有decript
        private List<SpuAttrVo> spuAttrVoList;
    }


}
