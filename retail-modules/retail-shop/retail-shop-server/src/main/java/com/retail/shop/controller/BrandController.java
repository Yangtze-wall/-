package com.retail.shop.controller;


import com.alibaba.fastjson.JSON;
import com.retail.common.result.Result;
import com.retail.shop.domain.BrandEntity;
import com.retail.shop.domain.ClassifiedEntity;
import com.retail.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 商品品牌表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/brandList")
    public Result brandList(){
        if (redisTemplate.hasKey("brandEntityList")){
            String brandEntityList = redisTemplate.opsForValue().get("brandEntityList");
            List<BrandEntity> brandEntityList1 = JSON.parseArray(brandEntityList, BrandEntity.class);
            return Result.success(brandEntityList1);
        }else {
            List<BrandEntity> brandEntityList = brandService.list();
            redisTemplate.opsForValue().set("brandEntityList",JSON.toJSONString(brandEntityList));
            return Result.success(brandEntityList);
        }
    }


}
