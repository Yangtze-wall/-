package com.retail.common.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.common.domain.vo
 * @ClassName:      InventoryVo
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/25 20:07
 * @Version:    1.0
 */
@Data
public class InventoryVo {

    private Long id;
    /**
     * 商品id
     */
    private Integer spuId;
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
