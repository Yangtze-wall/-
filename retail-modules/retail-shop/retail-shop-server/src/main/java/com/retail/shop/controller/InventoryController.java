package com.retail.shop.controller;

import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.InventoryEntity;
import com.retail.shop.mapper.InventoryMapper;
import com.retail.shop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 库存表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@RestController
@RequestMapping("shop/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Resource
    private InventoryMapper inventoryMapper;

    /**
     * 库存表详情
     *
     * @param spuId
     * @return
     */
    @PostMapping("/selectInventory")
    public InventoryVo selectInventory(@RequestParam("spuId") Long spuId) {
        if (spuId == null) {
            return null;
        }
        InventoryVo inventoryVo = inventoryService.selectInventory(spuId);

        return inventoryVo;
    }
    /**
     * 库存修改
     */
    @PostMapping("/updateInventory")
    public Result updateInventory(@RequestBody InventoryEntity inventoryEntity){
        int i = inventoryMapper.updateById(inventoryEntity);
        if (i < 0) {
            return Result.error("库存修改失败");
        }
        return Result.error("库存修改成功");
    }


}
