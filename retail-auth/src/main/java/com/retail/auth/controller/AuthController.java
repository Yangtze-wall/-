package com.retail.auth.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.retail.auth.service.AuthService;
import com.retail.auth.service.SmsService;
import com.retail.common.constant.Constants;
import com.alibaba.fastjson.JSON;
import com.retail.auth.service.AuthService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.response.JwtResponse;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.StringUtils;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author AuthController
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.controller
 * @date: 2023-03-23  17:20
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate<String,String>  redisTemplate;


    private HttpServletRequest request;



    @PostMapping("/register")
    public Result register(@RequestBody UserEntityRequest userEntityRequest){
        return authService.register(userEntityRequest);
    }

    @GetMapping("/userInfo")
    public Result<UserEntityVo> userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return Result.success(user);
    }

    @PostMapping("/loginPassword")
    public Result<JwtResponse> loginPassword(@RequestBody UserLoginPasswordVo userLoginPasswordVo){
        Result<JwtResponse> jwtResponseResult =  authService.loginPassword(userLoginPasswordVo);
        return jwtResponseResult;
    }
    @PostMapping("/sendSms")
    public Result sendSms(String phone){
        if (StringUtils.isBlank(phone)){
            throw new BizException(501,"手机号不能为空");
        }
        if (!Validator.isMobile(phone)){
            throw new BizException(501,"手机号不合法");
        }
        //随机生成验证码
        String code = RandomUtil.randomNumbers(6);
        redisTemplate.opsForValue().set(Constants.CODE_MSG+phone,code,5,TimeUnit.MINUTES);
        System.out.println(code);
        smsService.sendSms(phone,code);
        return Result.success("成功");

    }

}
