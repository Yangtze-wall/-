package com.retail.shop.service.impl;

import com.retail.common.domain.vo.GoodVo;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.mapper.SkuMapper;
import com.retail.shop.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;


@Service("skuService")
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    @Autowired
    private SkuMapper skuMapper;
    @Override
    public List<GoodVo> getInfo() {
        List<GoodVo> list =skuMapper.listGood();
        return list;
    }
}
