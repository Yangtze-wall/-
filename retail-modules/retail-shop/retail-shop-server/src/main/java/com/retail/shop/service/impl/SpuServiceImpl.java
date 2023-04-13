package com.retail.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.domain.vo.SpuVo;
import com.retail.shop.domain.SpuEntity;
import com.retail.shop.mapper.SpuMapper;
import com.retail.shop.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("spuService")
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {



    @Override
    public List<SpuEntity> selectListSpu() {
        List<SpuEntity> spuEntityList = this.baseMapper.selectList(null);
        return spuEntityList;
    }

    @Override
    public SpuVo selectSpu(Long spuId) {
        SpuVo spuVo = new SpuVo();
        SpuEntity spuEntity = this.baseMapper.selectOne(new QueryWrapper<SpuEntity>().lambda().eq(SpuEntity::getId, spuId));
        BeanUtils.copyProperties(spuEntity,spuVo);
        return spuVo;
    }
}
