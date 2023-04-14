package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginCodeVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserEntity;
import org.apache.ibatis.annotations.Param;

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

    Result<UserEntity> loginCode(UserLoginCodeVo userLoginCodeVo);




    Result<UserEntity> loginPasswordColonel(UserLoginPasswordVo userLoginPasswordVo);

    UserEntityVo findUserById(Long id);

    Result updateUser(UserEntityVo userEntityVo);

    UserEntityVo findUserInfoById();





//    UserEntityVo colonelLogin(String phone);


}

