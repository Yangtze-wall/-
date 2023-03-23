package com.retail.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.user.domain.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

}
