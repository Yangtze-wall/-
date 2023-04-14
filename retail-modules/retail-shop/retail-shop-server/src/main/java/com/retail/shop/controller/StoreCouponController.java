package com.retail.shop.controller;

import java.util.List;


import com.retail.common.domain.vo.StoreCouponEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.StoreCouponEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.shop.service.StoreCouponService;


/**
 * 优惠券表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@RestController
@RequestMapping("shop/storecoupon")
public class StoreCouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    //  shop/shop/storecoupon/storeCouponList
    @PostMapping("/storeCouponList")
    Result<List<StoreCouponEntity>> storeCouponList(){
        return storeCouponService.storeCouponList();
    }

    @GetMapping("/findByIdStoreCoupon/{id}")
    public Result<StoreCouponEntityVo> findByIdStoreCoupon(@PathVariable("id") Long id){
        return storeCouponService.findByIdStoreCoupon(id);
    }

}
