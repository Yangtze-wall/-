package com.retail.user.service.impl;

import com.retail.common.domain.vo.RoleVo;
import com.retail.user.domain.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.RoleMapper;
import com.retail.user.service.RoleService;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {



    @Override
    public List<RoleVo> selectOrderAddress2() {

        return null;
    }
}
