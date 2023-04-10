package com.retail.auth.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.retail.auth.domain.Captcha;
import com.retail.auth.service.AuthService;
import com.retail.auth.service.CaptchaService;
import com.retail.auth.service.SmsService;
import com.retail.common.constant.Constants;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.response.JwtResponse;
import com.retail.common.domain.vo.LoginVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private HttpServletRequest request;
    @PostMapping("upload")
    public Result update(@RequestParam("file") MultipartFile file){
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("图片不能为空！");
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String contentType = file.getContentType();
        byte[] imageBytes = null;
        String base64EncoderImg="";
        try {
            imageBytes = file.getBytes();
            BASE64Encoder base64Encoder =new BASE64Encoder();
            /**
             * 1.Java使用BASE64Encoder 需要添加图片头（"data:" + contentType + ";base64,"），
             *   其中contentType是文件的内容格式。
             * 2.Java中在使用BASE64Enconder().encode()会出现字符串换行问题，这是因为RFC 822中规定，
             *   每72个字符中加一个换行符号，这样会造成在使用base64字符串时出现问题，
             *   所以我们在使用时要先用replaceAll("[\\s*\t\n\r]", "")解决换行的问题。
             */
            base64EncoderImg = "data:" + contentType + ";base64," + base64Encoder.encode(imageBytes);
            base64EncoderImg = base64EncoderImg.replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Result.success(base64EncoderImg);
    }
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
    public Result<JwtResponse> loginPassword(@RequestBody LoginVo userLoginPasswordVo){
        String msg = captchaService.checkImageCode(userLoginPasswordVo.getNonceStr(),userLoginPasswordVo.getValue());
        if (!StringUtils.isNotBlank(msg)) {
            Result<JwtResponse> jwtResponseResult = authService.loginPassword(userLoginPasswordVo);
            return jwtResponseResult;

        }
        return null;
    }
    @PostMapping("/loginPassword/colonel")
    public Result<JwtResponse> loginPasswordColonel(@RequestBody LoginVo userLoginPasswordVo){
        Result<JwtResponse> jwtResponseResult =  authService.loginPasswordColonel(userLoginPasswordVo);
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
    //退出登录
    @GetMapping("logout")
    public Result logout(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        redisTemplate.hasKey(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        return Result.success(null,"退出");
    }
    @Autowired
    private CaptchaService captchaService;
    @PostMapping("get-captcha")
    public Result<Captcha> getCaptcha(@RequestBody Captcha captcha) {
        Captcha captcha1 = captchaService.getCaptcha(captcha);
        return Result.success(captcha1);
    }

}
