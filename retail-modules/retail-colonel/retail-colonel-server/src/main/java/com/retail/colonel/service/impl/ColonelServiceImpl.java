package com.retail.colonel.service.impl;

import com.retail.common.domain.vo.UserEntityVo;
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
    public List<ColonelEntity> selectColonel(UserEntityVo userEntityVo) {

        List<ColonelEntity> colonelEntityList = this.baseMapper.selectList(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getUserId, userEntityVo.getId()));


        return colonelEntityList;
    }
}
