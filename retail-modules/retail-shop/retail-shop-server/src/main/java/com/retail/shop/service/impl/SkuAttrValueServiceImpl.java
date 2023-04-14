package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.shop.domain.SkuAttrValueEntity;
import com.retail.shop.mapper.SkuAttrValueMapper;
import com.retail.shop.service.SkuAttrValueService;
import org.springframework.stereotype.Service;


@Service("skuAttrValueService")
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValueEntity> implements SkuAttrValueService {

}
