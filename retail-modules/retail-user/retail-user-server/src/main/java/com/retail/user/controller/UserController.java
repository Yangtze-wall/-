package com.retail.user.controller;

import java.util.List;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.OssUtil;
import com.retail.common.utils.StringUtils;
import com.retail.user.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.retail.user.service.UserService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */

@RestController
@RequestMapping("user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/colonelLogin/{phone}")
    Result<UserEntityVo> loginPasswordColonel(@PathVariable("phone") String phone){
        UserEntityVo user = this.userService.colonelLogin(phone);
        return Result.success(user,"调用成功");
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file")MultipartFile multipartFile){
        String file = OssUtil.uploadMultipartFile(multipartFile);
        return Result.success(file);
    }

    /**
     * 注册
     * @param userEntityRequest
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserEntityRequest userEntityRequest){
        return userService.register(userEntityRequest);
    }

    /**
     * 登陆
     * @param userLoginPasswordVo
     * @return
     */
    @PostMapping("/loginPassword")
    public Result<UserEntityVo> loginPassword(@RequestBody UserLoginPasswordVo userLoginPasswordVo){
        //判断不为空
        if (StringUtils.isBlank(userLoginPasswordVo.getPhone())){
            throw new BizException(502,"手机号不能为空");
        }
        //判断是否合法
        if (!Validator.isMobile(userLoginPasswordVo.getPhone())){
            throw new BizException(502,"手机号不合法");
        }

        if (StringUtils.isBlank(userLoginPasswordVo.getPassword())){
            throw new BizException(502,"密码不能为空");
        }

        Result<UserEntity> userEntityResult = userService.loginPassword(userLoginPasswordVo);
        UserEntity data = userEntityResult.getData();
        UserEntityVo userEntityVo = new UserEntityVo();
        BeanUtil.copyProperties(data,userEntityVo);
        return  Result.success(userEntityVo);
    }

    @PostMapping("/findByIdUser")
    public Result<UserEntityVo> findByIdUser(){
        return userService.findByIdUser();
    }
}
