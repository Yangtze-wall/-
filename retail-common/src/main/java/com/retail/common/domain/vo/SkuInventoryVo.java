package com.retail.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.common.domain.vo
 * @ClassName:      SkuInventoryVo
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/25 10:50
 * @Version:    1.0
 */
@Data
public class SkuInventoryVo {

    /**
     * sku表
     */

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
     * 库存
     */
    private Integer skuStock;
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
     * 库存表
     */

    private Long inventoryId;

    /**
     * 库存数量
     */
    private Integer inventoryCount;
    /**
     * 入库时间
     */
    private Date inventoryCreateTime;
    /**
     * 锁库存
     */
    private Integer inventoryLock;
    /**
     * 出售数量
     */
    private Integer inventorySellCount;











}
