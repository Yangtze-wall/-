package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.shop.mapper.RetaiUserCouponCenterMapper;
import com.retail.shop.domain.RetaiUserCouponCenterEntity;
import com.retail.shop.service.RetaiUserCouponCenterService;


@Service("retaiUserCouponCenterService")
public class RetaiUserCouponCenterServiceImpl extends ServiceImpl<RetaiUserCouponCenterMapper, RetaiUserCouponCenterEntity> implements RetaiUserCouponCenterService {


    @Override
    public Result isDelRetaiUserCoupon(Long couponId) {
        RetaiUserCouponCenterEntity retaiUserCouponCenterEntity = new RetaiUserCouponCenterEntity();
        retaiUserCouponCenterEntity.setCouponId(couponId);
        retaiUserCouponCenterEntity.setIsDel(1);
        baseMapper.update(retaiUserCouponCenterEntity,new QueryWrapper<RetaiUserCouponCenterEntity>().lambda().eq(RetaiUserCouponCenterEntity::getCouponId,couponId));
        return Result.success();
    }
}
