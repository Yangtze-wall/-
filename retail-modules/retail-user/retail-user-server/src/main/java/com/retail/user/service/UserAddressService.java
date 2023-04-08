package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.user.domain.UserAddressEntity;
import com.retail.common.domain.vo.UserAddressEntityVo;


import java.util.List;

/**
 * 收货地址表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
public interface UserAddressService extends IService<UserAddressEntity> {


    List<UserAddressEntityVo> selectStoreCouponByUserId();


    UserAddressEntityVo findUserAddressById(Long id);

}

