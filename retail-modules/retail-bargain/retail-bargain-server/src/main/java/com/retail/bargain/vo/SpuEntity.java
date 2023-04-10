package com.retail.bargain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.vo
 * @Description TODO
 * @createTime 2023/4/3 20:12
 */
@Data
public class SpuEntity {
    /**
     * 商品表
     */
    private Long id;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品介绍
     */
    private String spuLetter;

    /**
     * 商铺id
     */
    private Long shopId;

    /**
     * 商品上架时间
     */
    private Date spuCreateTime;

    /**
     * 商品修改时间
     */
    private Date spuUpdateTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 1 上架 0下架
     */
    private Integer spuStatus;

    /**
     * 1 同步 2 未同步
     */
    private Integer esStatus;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 所属分类id
     */
    private Long classifiedId;

    /**
     * 正常价
     */
    private BigDecimal spuPrice;

    /**
     * 拼团价
     */
    private BigDecimal spuColonelPrice;

}
