package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserLoginCodeVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserEntity;

/**
 * 用户表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
public interface UserService extends IService<UserEntity> {
     public UserEntity userInfo();

    Result register(UserEntityRequest userEntityRequest);

    Result<UserEntity> loginPassword(UserLoginPasswordVo userLoginPasswordVo);

    Result<UserEntity> loginCode(UserLoginCodeVo userLoginCodeVo);


}

