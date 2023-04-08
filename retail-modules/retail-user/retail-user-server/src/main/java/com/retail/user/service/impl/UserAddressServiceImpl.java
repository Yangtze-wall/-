package com.retail.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.TokenBucket;
import com.retail.user.domain.UserAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserAddressMapper;
import com.retail.user.service.UserAddressService;

import javax.servlet.http.HttpServletRequest;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {

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
    @Override
    public Result<List<UserAddressEntityVo>> findByUserIdList() {
        List<UserAddressEntity> userAddressEntityList = baseMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, userInfo().getId()));
        List<UserAddressEntityVo> userAddressEntityVoList = userAddressEntityList.stream().map(c -> {
            UserAddressEntityVo userAddressEntityVo = new UserAddressEntityVo();
            BeanUtil.copyProperties(c, userAddressEntityVo);
            return userAddressEntityVo;
        }).collect(Collectors.toList());
        return Result.success(userAddressEntityVoList);
    }

    @Override
    public Result<UserAddressEntityVo> findByIdAddress(Long id) {
        // 令牌桶
        //int maxTokens, double refillRate
        TokenBucket tokenBucket = new TokenBucket(1, 1);
        boolean b = tokenBucket.tryConsume(1);
        if (!b) {
            return Result.error();
        }
        UserAddressEntity userAddressEntity=baseMapper.selectOne(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getId,id));
        UserAddressEntityVo userAddressEntityVo = new UserAddressEntityVo();
        BeanUtil.copyProperties(userAddressEntity, userAddressEntityVo);
        return Result.success(userAddressEntityVo);
    }
}
