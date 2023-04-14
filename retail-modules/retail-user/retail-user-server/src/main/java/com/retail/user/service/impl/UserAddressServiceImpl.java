package com.retail.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.utils.JwtUtils;
import com.retail.user.domain.UserAddressEntity;
import com.retail.common.domain.vo.UserAddressEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserAddressMapper;
import com.retail.user.service.UserAddressService;

import javax.servlet.http.HttpServletRequest;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {


    /**
     * 根据用户id 找到用户下拉框
     * @return
     */
    @Override
    public List<UserAddressEntityVo> selectStoreCouponByUserId() {
        List<UserAddressEntity> userAddressEntityList = this.baseMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, userInfo().getId()));


        List<UserAddressEntityVo> collect = userAddressEntityList.stream().map(c -> {
            UserAddressEntityVo userAddressEntityVo = new UserAddressEntityVo();
            BeanUtil.copyProperties(c, userAddressEntityVo);
            return userAddressEntityVo;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public UserAddressEntityVo findUserAddressById(Long id) {
        UserAddressEntity userAddressEntity = this.baseMapper.selectById(id);
        UserAddressEntityVo userAddressEntityVo = new UserAddressEntityVo();
        BeanUtil.copyProperties(userAddressEntity,userAddressEntityVo);
        return userAddressEntityVo;
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
