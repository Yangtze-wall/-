package com.retail.user.service.impl;

import com.retail.user.domain.RoleEntity;
import com.retail.user.domain.UserRoleEntity;
import com.retail.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<UserRoleEntity> selectUserRole() {
        List<UserRoleEntity> list = this.userRoleMapper.selectList(null);
        return list;
    }

    @Override
    public UserRoleEntity findById(Long id) {
        UserRoleEntity userRoleEntity=this.userRoleMapper.selectById(id);
        return userRoleEntity;
    }

    @Override
    public void insert(UserRoleEntity params) {
        this.userRoleMapper.insert(params);
    }

    @Override
    public void delete(String id) {
        this.userRoleMapper.deleteById(id);
    }

    @Override
    public void updateRole(UserRoleEntity params) {
        this.userRoleMapper.updateById(params);
    }
}
