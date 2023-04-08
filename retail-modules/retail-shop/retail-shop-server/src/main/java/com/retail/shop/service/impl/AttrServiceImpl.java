package com.retail.shop.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.shop.domain.AttrEntity;
import com.retail.shop.mapper.AttrMapper;
import com.retail.shop.service.AttrService;
import org.springframework.stereotype.Service;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {


}
