package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserEntity;

import java.io.IOException;

/**
 * 用户表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
public interface UserService extends IService<UserEntity> {


    Result register(UserEntityRequest userEntityRequest);

    Result<UserEntity> loginPassword(UserLoginPasswordVo userLoginPasswordVo);

    UserEntityVo colonelLogin(String phone);

    Result<UserEntityVo> findByIdUser();

    Result<String> authentication(String realName, String idCard);

    Result<String> biopsy(String url) throws IOException;

    Result updateIntegration(UserEntity userEntity);
}

