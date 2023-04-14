package com.retail.shop.controller;

import java.util.List;


import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.InventoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping("findInventoryBySpuId/{spuId}")
    public Result<InventoryEntityVo> findInventoryBySpuId(@PathVariable("spuId")Long spuId){
        InventoryEntityVo inventoryEntityVo=   inventoryService.findInventoryBySpuId(spuId);
        return Result.success(inventoryEntityVo);
    }

    @PostMapping("updateInventory")
    public Result updateInventory(@RequestBody InventoryEntityVo inventoryEntityVo){

        return inventoryService.updateInventory(inventoryEntityVo);
    }

}
