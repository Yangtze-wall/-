package com.retail.colonel.config;

import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName GetInfo
 * Date 2023/3/27 14:35
 **/
@Component
public  class UserInfo {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public   UserEntityVo getInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }
}
