package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.retail.user.domain.RoleEntity;
import com.retail.user.domain.UserRoleEntity;


import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
public interface RoleService extends IService<RoleEntity> {


    List<UserRoleEntity> selectUserRole();

    UserRoleEntity findById(Long id);

    void insert(UserRoleEntity params);

    void delete(String id);

    void updateRole(UserRoleEntity params);
}

