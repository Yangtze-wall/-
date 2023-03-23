package com.retail.shop.service.impl;

import com.retail.shop.domain.BrandEntity;
import com.retail.shop.mapper.BrandMapper;
import com.retail.shop.service.BrandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {



}
