package com.retail.shop.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.shop.domain.vo.RetaiUserCouponCenterEntityVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 联查 查询用户id 所具有的优惠卷和优惠卷金额
     * @return
     */
    @PostMapping("/selectStoreCouponByUserId")
    public Result<List<RetaiUserCouponCenterEntityVo>> selectStoreCouponByUserId(){

        List<RetaiUserCouponCenterEntityVo> retaiUserCouponCenterEntityVoList=retaiUserCouponCenterService.selectStoreCouponByUserId();
        return Result.success(retaiUserCouponCenterEntityVoList);
    }

}
