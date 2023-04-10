package com.retail.shop.controller;


import com.retail.common.domain.vo.GoodVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("sku")
public class SkuController {
    @Autowired
    private SkuService skuService;
    @GetMapping("/info/{id}")
    SkuEntity getInfo(@PathVariable("id") Long id){
        SkuEntity byId = skuService.getById(id);
        return byId;
    }
    @PostMapping("getInfo")
    public Result<List<GoodVo>> getInfo(){
        List<GoodVo> list =skuService.getInfo();
        return Result.success(list);
    }

}
