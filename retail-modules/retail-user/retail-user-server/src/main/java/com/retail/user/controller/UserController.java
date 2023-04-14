package com.retail.user.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginCodeVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.user.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.retail.user.service.UserService;

import javax.servlet.http.HttpServletRequest;


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
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
     * 密码登陆
     * @param userLoginPasswordVo
     * @return
     */
    @PostMapping("/loginPassword")
    public Result<UserEntityVo> loginPassword(@RequestBody UserLoginPasswordVo userLoginPasswordVo){
        //判断不为空
        if (StringUtils.isBlank(userLoginPasswordVo.getPhone())){
            return Result.error("手机号不能为空");
        }
        //判断是否合法
        if (!Validator.isMobile(userLoginPasswordVo.getPhone())){
            return Result.error("手机号不合法");
        }

        if (StringUtils.isBlank(userLoginPasswordVo.getPassword())){
            return Result.error("密码不能为空");
        }

        Result<UserEntity> userEntityResult = userService.loginPassword(userLoginPasswordVo);
        UserEntity data = userEntityResult.getData();
        UserEntityVo userEntityVo = new UserEntityVo();
        BeanUtil.copyProperties(data,userEntityVo);
        return  Result.success(userEntityVo);
    }
//        @PostMapping("/loginPassword/{phone}")
//    Result<UserEntityVo> loginPassword(@PathVariable("phone") String phone){
//        UserEntityVo user = this.userService.loginPassword(phone);
//        return Result.success(user,"调用成功");
//    }

    @PostMapping("/loginCode")
    public Result<UserEntityVo> loginCode(@RequestBody UserLoginCodeVo userLoginCodeVo){
        //判断不为空
        if (StringUtils.isBlank(userLoginCodeVo.getPhone())){
//            throw new BizException(502,"手机号不能为空");
            return Result.error("手机号不能为空");
        }
        //判断是否合法
        if (!Validator.isMobile(userLoginCodeVo.getPhone())){
//            throw new BizException(502,"手机号不合法");
            return Result.error("手机号不合法");
        }
        if (StringUtils.isBlank(userLoginCodeVo.getCode())){
//            throw new BizException(502,"验证码不能为空");
            return Result.error("验证码不能为空");
        }
        Result<UserEntity> userEntityResult = userService.loginCode(userLoginCodeVo);
        UserEntity data = userEntityResult.getData();
        UserEntityVo userEntityVo = new UserEntityVo();
        BeanUtil.copyProperties(data,userEntityVo);
        return  Result.success(userEntityVo);
    }
    @PostMapping("/loginPasswordColonel")
    public Result<UserEntityVo> loginPasswordColonel(@RequestBody UserLoginPasswordVo userLoginPasswordVo){
        //判断不为空
        if (StringUtils.isBlank(userLoginPasswordVo.getPhone())){
            return Result.error("手机号不能为空");
        }
        //判断是否合法
        if (!Validator.isMobile(userLoginPasswordVo.getPhone())){
            return Result.error("手机号不合法");
        }
        if (StringUtils.isBlank(userLoginPasswordVo.getPassword())){
            return Result.error("密码不能为空");
        }
        Result<UserEntity> userEntityResult = userService.loginPasswordColonel(userLoginPasswordVo);
        UserEntity data = userEntityResult.getData();
        if (data==null){
            return Result.error("还不是团长");
        }
        UserEntityVo userEntityVo = new UserEntityVo();
        BeanUtil.copyProperties(data,userEntityVo);
        return  Result.success(userEntityVo);
    }

//    @PostMapping("/colonelLogin/{phone}")
//    Result<UserEntityVo> loginPasswordColonel(@PathVariable("phone") String phone){
//        UserEntityVo user = this.userService.colonelLogin(phone);
//        return Result.success(user,"调用成功");
//    }



    @GetMapping("/userInfo")
    public Result<UserEntityVo> userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return Result.success(user);
    }

    /**
     * 查找用户 数据
     * @param
     * @return
     */
    @PostMapping("/findUserInfoById")
    public Result<UserEntityVo> findUserInfoById(){
        UserEntityVo userEntityVo=userService.findUserInfoById();
        return Result.success(userEntityVo);
    }

    @PostMapping("/findUserById/{id}")
    public Result<UserEntityVo> findUserById(@PathVariable("id")Long id){
        UserEntityVo userEntityVo=userService.findUserById(id);
        return Result.success(userEntityVo);
    }


    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserEntityVo userEntityVo){

        return userService.updateUser(userEntityVo);
    }


}
