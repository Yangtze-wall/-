package com.retail.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.shop.mapper.StoreCouponMapper;
import com.retail.shop.domain.StoreCouponEntity;
import com.retail.shop.service.StoreCouponService;


@Service("storeCouponService")
public class StoreCouponServiceImpl extends ServiceImpl<StoreCouponMapper, StoreCouponEntity> implements StoreCouponService {


    @Override
    public StoreCouponEntity findStoreCouponById(Long id) {
        StoreCouponEntity storeCouponEntity = this.baseMapper.selectById(id);
        return storeCouponEntity;
    }
}
