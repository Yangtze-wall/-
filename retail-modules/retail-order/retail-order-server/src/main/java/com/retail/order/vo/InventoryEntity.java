package com.retail.order.vo;

import lombok.Data;

import java.util.Date;

/**
 * 库存表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
public class InventoryEntity {

    /**
     * 库存表主键
     */
    private Long id;
    /**
     * 商品id
     */
    private Long spuId;
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
