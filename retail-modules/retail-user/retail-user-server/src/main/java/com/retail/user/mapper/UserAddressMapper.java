package com.retail.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.user.domain.UserAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 收货地址表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddressEntity> {

    void update1(@Param("userId") Long userId);

    void update2(@Param("id") Long id);
}
