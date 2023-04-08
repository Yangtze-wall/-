package com.retail.shop.domain.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class SkuModelVo {

    private Long skuId;
    /**
     * 商品图片
     */
    private String skuImage;
    /**
     * 商品标题
     */
    private String skuTitle;
    /**
     * 副标题
     */
    private String skuSubhead;
    /**
     * 销量
     */
    private Integer skuSell;
    /**
     * 库存
     */
    private Integer inventoryCount;
    /**
     * 价格
     */
    private BigDecimal skuPrice;
    /**
     * spuid
     */
    private Long spuId;
    /**
     * 品牌id
     */
    private Long brandId;
    private String  brandName;//品牌名
    private String brandLogo;//品牌logo
    /**
     * 分类id
     */
    private Long classifiedId;
    private String classifiedName;//分类名称

    //商品状态
    private Integer spuStatus;





}
