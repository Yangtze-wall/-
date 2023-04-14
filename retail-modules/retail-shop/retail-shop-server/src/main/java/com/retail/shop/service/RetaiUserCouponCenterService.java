package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.shop.domain.RetaiUserCouponCenterEntity;
import com.retail.shop.domain.vo.RetaiUserCouponCenterEntityVo;

import java.util.List;
import java.util.Map;

/**
 * 用户/优惠卷中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
public interface RetaiUserCouponCenterService extends IService<RetaiUserCouponCenterEntity> {


    List<RetaiUserCouponCenterEntityVo> selectStoreCouponByUserId();


}

