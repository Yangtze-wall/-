package com.retail.shop.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.retail.shop.vo
 * @Description TODO
 * @createTime 2023/4/11 11:25
 */
@Data
public class GoodsVo {
    private Long id;
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
    /**
     * 分类id
     */
    private Long classifiedId;

    /**
     * 商品类型
     */
    private String classifiedName;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品属性
     */
    private String attrValue;
}
