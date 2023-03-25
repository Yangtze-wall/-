package com.retail.colonel.service.impl;

import com.retail.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelMapper;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.service.ColonelService;


@Service("colonelService")
public class ColonelServiceImpl extends ServiceImpl<ColonelMapper, ColonelEntity> implements ColonelService {


    @Override
    public Result coloneInsert(ColonelEntity colonelEntity) {
        int insert = baseMapper.insert(colonelEntity);
        if (insert<=0){
            return Result.error();
        }
        return Result.success("团长添加成功");
    }

    @Override
    public List<ColonelEntity> select() {
        return baseMapper.selectList(new QueryWrapper<ColonelEntity>());
    }

    @Override
    public Result coloneUpdate(ColonelEntity colonelEntity) {
        baseMapper.updateById(colonelEntity);
        return Result.success();
    }

    @Override
    public Result<ColonelEntity> findByIdColonelEntity(Long id) {
        ColonelEntity colonelEntity = baseMapper.selectOne(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getId, id));
        return Result.success(colonelEntity);
    }

    @Override
    public Result deleteColonelEntity(Long id) {
        baseMapper.delete(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getId,id));
        return Result.success();
    }
}
