package com.retail.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.StoreCouponEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.shop.mapper.StoreCouponMapper;
import com.retail.shop.domain.StoreCouponEntity;
import com.retail.shop.service.StoreCouponService;

import javax.servlet.http.HttpServletRequest;


@Service("storeCouponService")
public class StoreCouponServiceImpl extends ServiceImpl<StoreCouponMapper, StoreCouponEntity> implements StoreCouponService {

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
    public Result<List<StoreCouponEntity>> storeCouponList() {
        List<StoreCouponEntity> storeCouponEntityList =baseMapper.storeCouponList(userInfo().getId());
        return Result.success(storeCouponEntityList);
    }

    @Override
    public Result<StoreCouponEntityVo> findByIdStoreCoupon(Long id) {
        StoreCouponEntity storeCouponEntity=baseMapper.selectOne(new QueryWrapper<StoreCouponEntity>().lambda().eq(StoreCouponEntity::getId,id));
        StoreCouponEntityVo storeCouponEntityVo = new StoreCouponEntityVo();
        BeanUtil.copyProperties(storeCouponEntity,storeCouponEntityVo);
        return Result.success(storeCouponEntityVo);
    }


}
