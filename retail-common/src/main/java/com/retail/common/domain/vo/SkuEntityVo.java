package com.retail.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author SkuEntityVo
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.domain.vo
 * @date: 2023-03-27  21:13
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Data
public class SkuEntityVo {
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
}
