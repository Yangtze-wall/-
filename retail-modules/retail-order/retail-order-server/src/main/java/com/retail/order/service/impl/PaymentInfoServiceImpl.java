package com.retail.order.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.domain.PaymentEntity;
import com.retail.order.mapper.PaymentMapper;
import com.retail.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentMapper, PaymentEntity> implements PaymentService {
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
    public Result<List<PaymentEntity>> getPaymentList() {
        List<PaymentEntity> paymentEntityList=baseMapper.selectList(new QueryWrapper<PaymentEntity>());
        return Result.success(paymentEntityList);
    }
}
