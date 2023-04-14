package com.retail.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.utils.JwtUtils;
import com.retail.shop.domain.vo.RetaiUserCouponCenterEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.shop.mapper.RetaiUserCouponCenterMapper;
import com.retail.shop.domain.RetaiUserCouponCenterEntity;
import com.retail.shop.service.RetaiUserCouponCenterService;

import javax.servlet.http.HttpServletRequest;


@Service("retaiUserCouponCenterService")
public class RetaiUserCouponCenterServiceImpl extends ServiceImpl<RetaiUserCouponCenterMapper, RetaiUserCouponCenterEntity> implements RetaiUserCouponCenterService {

    @Autowired
    private RetaiUserCouponCenterMapper retaiUserCouponCenterMapper;

    @Override
    public List<RetaiUserCouponCenterEntityVo> selectStoreCouponByUserId() {
       List<RetaiUserCouponCenterEntityVo> retaiUserCouponCenterEntityVoList= retaiUserCouponCenterMapper.selectStoreCouponByUserId(userInfo().getId());
        return retaiUserCouponCenterEntityVoList;
    }
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }
}
