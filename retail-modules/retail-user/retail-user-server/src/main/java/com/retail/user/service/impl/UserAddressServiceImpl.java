package com.retail.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.AddressVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.utils.JwtUtils;
import com.retail.user.domain.UserAddressEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserAddressMapper;
import com.retail.user.service.UserAddressService;

import javax.servlet.http.HttpServletRequest;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<AddressVo> selectOrderAddress(Long id) {
        List<UserAddressEntity> userAddressEntityList = this.baseMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, id));

        List<AddressVo> collect = userAddressEntityList.stream().map(c -> {
            AddressVo addressVo = new AddressVo();
            BeanUtils.copyProperties(c, addressVo);
            return addressVo;
        }).collect(Collectors.toList());


        return collect;
    }

    @Override
    public List<AddressVo> selectOrderAddressTwo() {
        UserEntityVo userEntityVo = this.userInfo();
        Long id = userEntityVo.getId();

        List<UserAddressEntity> userAddressEntityList = this.baseMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, id));

        List<AddressVo> collect = userAddressEntityList.stream().map(c -> {
            AddressVo addressVo = new AddressVo();
            BeanUtils.copyProperties(c, addressVo);
            return addressVo;
        }).collect(Collectors.toList());


        return collect;
    }

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }

}
