package com.retail.colonel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.mapper.ColonelMapper;
import com.retail.colonel.service.ColonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("colonelService")
public class ColonelServiceImpl extends ServiceImpl<ColonelMapper, ColonelEntity> implements ColonelService {
    @Autowired
    private ColonelMapper colonelMapper;

    @Override
    public ColonelEntity findById(Long id) {
        ColonelEntity colonelEntity = this.colonelMapper.selectById(id);
        return colonelEntity;
    }

    @Override
    public ColonelEntity getInfo(Long id) {
        ColonelEntity colonelEntity = this.colonelMapper.selectOne(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getUserId, id));
        return colonelEntity;
    }
}
