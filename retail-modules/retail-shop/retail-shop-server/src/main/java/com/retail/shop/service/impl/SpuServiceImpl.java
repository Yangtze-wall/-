package com.retail.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.result.Result;

import com.retail.shop.domain.*;

import com.retail.shop.mapper.SpuMapper;
import com.retail.shop.service.*;

import org.springframework.stereotype.Service;




@Service("spuService")
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {


    @Override
    public Result insertSpu(SpuEntity spuEntity) {
        baseMapper.insert(spuEntity);
        return Result.success("成功");
    }

    @Override
    public Result deleteSpuById(Long id) {
        SpuEntity spuEntity = new SpuEntity();
        spuEntity.setId(id);
        spuEntity.setSpuStatus(2);
        baseMapper.updateById(spuEntity);
        return Result.success("成功");
    }

    @Override
    public Result updateSpu(SpuEntity spuEntity) {
        baseMapper.update(spuEntity,new UpdateWrapper<SpuEntity>().lambda().eq(SpuEntity::getId,spuEntity.getId()));
        return Result.success("成功");
    }




}
