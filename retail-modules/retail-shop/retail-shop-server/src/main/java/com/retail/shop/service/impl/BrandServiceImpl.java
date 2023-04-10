package com.retail.shop.service.impl;

import com.retail.shop.domain.BrandEntity;
import com.retail.shop.mapper.BrandMapper;
import com.retail.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

}
