package com.retail.colonel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelApplyMapper;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.service.ColonelApplyService;


@Service("colonelApplyService")
public class ColonelApplyServiceImpl extends ServiceImpl<ColonelApplyMapper, ColonelApplyEntity> implements ColonelApplyService {


    @Override
    public Result colonelApplyInsert(ColonelApplyEntity colonelApplyEntity) {
        int insert = baseMapper.insert(colonelApplyEntity);
        if (insert<=0){
            return Result.error();
        }
        return Result.success("团长申请添加成功");
    }

    @Override
    public List<ColonelApplyEntity> select() {
        return baseMapper.selectList(new QueryWrapper<ColonelApplyEntity>());
    }

    @Override
    public Result coloneApplyUpdate(ColonelApplyEntity colonelApplyEntity) {
        baseMapper.updateById(colonelApplyEntity);
        return Result.success();
    }

    @Override
    public Result<ColonelApplyEntity> findByIdColoneApplyEntity(Long id) {
        ColonelApplyEntity colonelApplyEntity = baseMapper.selectOne(new QueryWrapper<ColonelApplyEntity>().lambda().eq(ColonelApplyEntity::getId, id));
        return Result.success(colonelApplyEntity);
    }

    @Override
    public Result deleteColonelApplyEntity(Long id) {
         baseMapper.delete(new QueryWrapper<ColonelApplyEntity>().lambda().eq(ColonelApplyEntity::getId,id));
        return Result.success();
    }


}
