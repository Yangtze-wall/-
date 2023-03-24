package com.retail.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.user.constant.Constant;
import com.retail.user.domain.PowerUserEntity;

import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;

import com.retail.user.domain.UserEntity;
import com.retail.user.service.PowerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserMapper;
import com.retail.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private PowerUserService powerUserService;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result register(UserEntityRequest userEntityRequest) {
        if (StringUtils.isEmpty(userEntityRequest.getUsername())){
            throw new BizException(501,"请输入账号");
        }
        if (StringUtils.isEmpty(userEntityRequest.getPassword())){
            throw new  BizException(501,"请输入密码");
        }
        if (StringUtils.isEmpty(userEntityRequest.getPasswordVerify())){
            throw new  BizException(501,"确认密码不能为空");
        }
        if (userEntityRequest.getUsername().equals(userEntityRequest.getPasswordVerify())){
            throw new  BizException(501,"密码与确认密码不同");
        }
        if (StringUtils.isEmpty(userEntityRequest.getPhone())){
            throw new  BizException(501,"手机号不能为空");
        }
//        if (StringUtils.isEmpty(userEntityRequest.getCode())){
//            throw new  BizException(501,"短信不能为空");
//        }
//        String s = redisTemplate.opsForValue().get(Constant.CODE_MSG + userEntityRequest.getPhone());
//        if (!userEntityRequest.getCode().equals(s)){
//            throw new  BizException(501,"短信不一致");
//        }
        UserEntity userEntityUserName = baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getUsername, userEntityRequest.getUsername()));
        if (userEntityUserName!=null){
            throw new  BizException(501,"账号存在,请重新注册");
        }
        UserEntity userEntityPhone = baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getPhone, userEntityRequest.getPhone()));
        if (userEntityPhone!=null){
            throw new  BizException(501,"手机号已经注册,请重新注册");
        }
        //new 出对象
        UserEntity userEntity = new UserEntity();
        // 账号写入
        userEntity.setUsername(userEntityRequest.getUsername());
        // 加密言
        String salt = RandomUtil.randomNumbers(6);
        userEntity.setSalt(salt);
        // 密码
        String password = SecureUtil.md5(userEntityRequest.getPassword()+"|"+salt);
        userEntity.setPassword(password);
        //手机号
        userEntity.setPhone(userEntityRequest.getPhone());
        //注册时间
        userEntity.setCreateTime(new Date());
        //用户余额
        userEntity.setBalance(0);
        //购物积分
        userEntity.setIntegration(0);
        baseMapper.insert(userEntity);

        PowerUserEntity powerUserEntity = new PowerUserEntity();
        powerUserEntity.setUserId(userEntity.getId());
        powerUserEntity.setPowerId(1L);
        powerUserService.save(powerUserEntity);
        return Result.success("注册成功");
    }
    @Override
    public UserEntity userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntity user = JSON.parseObject(s, UserEntity.class);
        return  user;
    }
    @Override
    public Result<UserEntity> loginPassword(UserLoginPasswordVo userLoginPasswordVo) {
        //判断用户是否存在

        UserEntity userEntity = baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getPhone, userLoginPasswordVo.getPhone()));
        if (userEntity==null){
            throw  new BizException(502,"用户没有注册，请注册");
        }

        return Result.success(userEntity);
    }
}
