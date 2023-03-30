package com.retail.shop.controller;

import java.util.List;


import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.retail.shop.service.InventoryService;


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

    /**
     * 查询商品库存
     * @param spuId
     * @return
     */
    @PostMapping("/findByInventoryEntity/{spuId}")
    Result<InventoryEntityVo> findByInventoryEntity(@PathVariable("spuId") Long spuId){
        return inventoryService.findByInventoryEntity(spuId);
    }

    @PutMapping("/updateInventoryLock")
    public Result updateInventoryLock(@RequestBody InventoryEntityVo inventoryEntityVo){
        return inventoryService.updateInventoryLock(inventoryEntityVo);
    }
}
