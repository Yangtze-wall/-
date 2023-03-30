package com.retail.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.vo.StoreCouponEntityVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.StoreCouponEntity;

import java.util.List;
import java.util.Map;

/**
 * 优惠券表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
public interface StoreCouponService extends IService<StoreCouponEntity> {


    Result<List<StoreCouponEntity>> storeCouponList();

    Result<StoreCouponEntityVo> findByIdStoreCoupon(Long id);
}

