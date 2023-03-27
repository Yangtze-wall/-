package com.retail.colonel.controller;

import com.alibaba.fastjson.JSON;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.domain.vo.ColonelInfoVo;
import com.retail.colonel.service.ColonelService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("colonel/colonel")
public class ColonelController {

    @Autowired
    private ColonelService colonelService;
    //团长的个人主页
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String > redisTemplate;
    @GetMapping("colonelInfo")
    public Result<ColonelInfoVo> colonelInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        ColonelEntity colonelEntity =colonelService.findById(user.getId());
        ColonelInfoVo colonelInfoVo = new ColonelInfoVo();
        colonelInfoVo.setUser(user);
        colonelInfoVo.setColonel(colonelEntity);
        return Result.success(colonelInfoVo);
    }
    //佣金
    @GetMapping("commissionInfo")
    public Result<ColonelEntity> commission(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        ColonelEntity colonelEntity =  colonelService.getInfo(user.getId());
        return Result.success(colonelEntity);
    }

}
