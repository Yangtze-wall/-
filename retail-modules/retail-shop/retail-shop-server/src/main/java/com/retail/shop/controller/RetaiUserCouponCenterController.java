package com.retail.shop.controller;

import java.util.List;


import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.shop.service.RetaiUserCouponCenterService;


/**
 * 用户/优惠卷中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@RestController
@RequestMapping("shop/retaiusercouponcenter")
public class RetaiUserCouponCenterController {

    @Autowired
    private RetaiUserCouponCenterService retaiUserCouponCenterService;

    @PostMapping("shop/retaiusercouponcenter/isDelRetaiUserCoupon")
    Result isDelRetaiUserCoupon(@RequestParam("couponId") Long couponId){
        return retaiUserCouponCenterService.isDelRetaiUserCoupon(couponId);
    }
}
