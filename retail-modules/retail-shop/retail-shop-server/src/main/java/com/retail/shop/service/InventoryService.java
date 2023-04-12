package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.InventoryVo;
import com.retail.shop.domain.InventoryEntity;

/**
 * 库存表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
public interface InventoryService extends IService<InventoryEntity> {


    /**
     * 库存表详情
     * @param spuId
     * @return
     */
    InventoryVo selectInventory(Long spuId);
}

