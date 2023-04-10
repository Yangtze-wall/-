package com.retail.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName GoodVo
 * Date 2023/4/8 11:18
 **/
@Data
public class GoodVo {
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

    private String classifiedName;
    private String brandName;
    private String attrValue;
}
