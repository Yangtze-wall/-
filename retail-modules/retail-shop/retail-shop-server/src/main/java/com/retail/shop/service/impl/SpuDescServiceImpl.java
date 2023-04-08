package com.retail.shop.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.shop.domain.SpuDescEntity;
import com.retail.shop.mapper.SpuDescMapper;
import com.retail.shop.service.SpuDescService;
import org.springframework.stereotype.Service;


@Service("spuDescService")
public class SpuDescServiceImpl extends ServiceImpl<SpuDescMapper, SpuDescEntity> implements SpuDescService {


}
