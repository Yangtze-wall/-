package com.retail.shop.controller;


import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/findBySkuEntry/{spuId}")
    public Result<SkuEntityVo> findBySkuEntry(@PathVariable("spuId") Long spuId){
        return skuService.findBySkuEntry(spuId);
    }

    @PostMapping("/updateSkuSell")
    public Result updateSkuSell(@RequestBody SkuEntityVo skuEntityVo){
        return skuService.updateSkuSell(skuEntityVo);
    }

}
