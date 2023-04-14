package com.retail.user.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.user.constant.UserConstant;
import com.retail.user.domain.PowerUserEntity;
import com.retail.user.domain.UserEntity;
import com.retail.user.domain.UserRoleEntity;
import com.retail.user.service.PowerUserService;
import com.retail.user.service.UserRoleService;
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
    private UserRoleService userRoleService;

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
        // 初始状态  正常
        userEntity.setStatus(0);
        // 介绍
        userEntity.setRemark("用户很懒什么都没有写");
        baseMapper.insert(userEntity);
        // 权限
        PowerUserEntity powerUserEntity = new PowerUserEntity();
        powerUserEntity.setUserId(userEntity.getId());
        powerUserEntity.setPowerId(1L);
        powerUserService.save(powerUserEntity);
        //角色
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(1L);
        userRoleEntity.setUserId(userEntity.getId());
        userRoleService.save(userRoleEntity);
        return Result.success("注册成功");
    }

    @Override
    public Result<UserEntity> loginPassword(UserLoginPasswordVo userLoginPasswordVo) {
        //判断用户是否存在

        UserEntity userEntity = baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getPhone, userLoginPasswordVo.getPhone()));
        if (userEntity==null){
            throw  new BizException(502,"用户没有注册，请注册");
        }

        //写入最后登录时间
        userEntity.setLoginDate(new Date());
        baseMapper.update(userEntity,new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,userEntity.getId()));

        return Result.success(userEntity);
    }

    @Override
    public UserEntityVo colonelLogin(String phone) {
        UserEntityVo user = this.baseMapper.selectColonel(phone);
        if (StringUtils.isNull(user)){
            throw new BizException(403,"还不是团长");
        }
        user.setStatus(3);
        return user;
    }

    @Override
    public Result<UserEntityVo> findByIdUser(Long userId) {
        UserEntity userEntity=baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, userId));
        UserEntityVo userEntityVo = new UserEntityVo();

        BeanUtil.copyProperties(userEntity,userEntityVo);
        return Result.success(userEntityVo);
    }

    @Override
    public Result updateIntegration(UserEntity userEntity) {
        baseMapper.update(userEntity,new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,userEntity.getId()));
        return Result.success();
    }


    @Override
    public Result<String> authentication(String realName, String idCard) {
        String host = UserConstant.AUTHENTICATION_HOST;
        String appcode = "72bf2a3cc98a4b6e960f9b3e79c4a0e0";
        Map map = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        map.put("idCardNo",idCard);
        map.put("name",realName);
        String str = HttpUtil.createPost(host).method(Method.POST).header("Authorization", "APPCODE " + appcode).form(map).execute().body();
        System.out.println("response"+ str);
        return Result.success(str);
    }

    @Override
    public Result<String> biopsy(String url) throws IOException {
        String host = "https://life.shumaidata.com/checklife";
        String appCode = "72bf2a3cc98a4b6e960f9b3e79c4a0e0";
        Map params = new HashMap<>();
        params.put("complexity", "1");
        params.put("motions", "BLINK");
//        params.put("motions", "MOUTH");
//        params.put("motions", "NOD");
//        params.put("motions", "YAW");
        params.put("url", url);
        String str = HttpUtil.createPost(host).method(Method.POST).header("Authorization", "APPCODE " + appCode).form(params).execute().body();
        System.out.println("response:"+ str);
        return Result.success(str);
    }



    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }


}
