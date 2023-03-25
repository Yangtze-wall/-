package com.retail.auth.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.retail.auth.service.AuthService;
import com.retail.auth.service.SmsService;
import com.retail.auth.vo.SmsParamVo;
import com.retail.common.constant.Constants;
import com.alibaba.fastjson.JSON;
import com.retail.auth.service.AuthService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.response.JwtResponse;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginCodeVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
=======
import com.retail.common.utils.JwtUtils;
>>>>>>> master
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
@Log4j2
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate<String,String>  redisTemplate;


    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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

    /**
     * 手机号密码登录
     * @param userLoginPasswordVo
     * @return
     */
    @PostMapping("/loginPassword")
    public Result<JwtResponse> loginPassword(@RequestBody UserLoginPasswordVo userLoginPasswordVo){
        Result<JwtResponse> jwtResponseResult =  authService.loginPassword(userLoginPasswordVo);
        return jwtResponseResult;
    }

    /**
     * 短信验证码 登录
     * @param userLoginCodeVo
     * @return
     */
    @PostMapping("/loginCode")
    public Result<JwtResponse> loginCode(@RequestBody UserLoginCodeVo userLoginCodeVo){
        Result<JwtResponse> jwtResponseResult =  authService.loginCode(userLoginCodeVo);
        return jwtResponseResult;
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
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
        log.info("验证码"+code);
        //        smsService.sendSms(phone,code);
        SmsParamVo smsParamVo = new SmsParamVo();
        //设置id 唯一性(UUID)
        smsParamVo.setMsgId(IdUtil.randomUUID());
        smsParamVo.setPhone(phone);
        smsParamVo.setCode(code);
        rabbitTemplate.convertAndSend("retail.exchange.sms","retail.queue.sms", JSON.toJSONString(smsParamVo));

        return Result.success("成功");

    }

}
