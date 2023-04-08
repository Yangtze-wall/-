package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.shop.domain.SkuImagesEntity;
import com.retail.shop.mapper.SkuImagesMapper;
import com.retail.shop.service.SkuImagesService;
import org.springframework.stereotype.Service;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImagesEntity> implements SkuImagesService {


}
