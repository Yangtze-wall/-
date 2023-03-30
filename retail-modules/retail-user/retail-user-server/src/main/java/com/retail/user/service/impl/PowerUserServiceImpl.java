package com.retail.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.user.config.ThreadConfig;
import com.retail.user.domain.PowerEntry;
import com.retail.user.domain.UserEntity;
import com.retail.user.domain.vo.UserEntityPowerListVo;
import com.retail.user.domain.vo.UserEntryPowerVo;
import com.retail.user.mapper.PowerMapper;
import com.retail.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.PowerUserMapper;
import com.retail.user.domain.PowerUserEntity;
import com.retail.user.service.PowerUserService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.split;


@Service("powerUserService")
public class PowerUserServiceImpl extends ServiceImpl<PowerUserMapper, PowerUserEntity> implements PowerUserService {

    @Autowired
    private PowerUserMapper powerUserMapper;

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThreadConfig threadConfig;

    @Override
    public List<UserEntryPowerVo> getPowerUserEntryList() {
        List<UserEntryPowerVo> userEntryPowerVoList=powerUserMapper.list();
        return userEntryPowerVoList;
    }

    @Override
    public UserEntityPowerListVo findByIdUserPower(Long id) {


        ThreadPoolExecutor threadPoolExecutor = threadConfig.threadPoolExecutor();

        UserEntityPowerListVo userEntityPowerListVo = new UserEntityPowerListVo();
//        UserEntityVo userEntityVo = userInfo();

//        List<PowerUserEntity> powerUserEntities = powerUserMapper.selectList(new QueryWrapper<PowerUserEntity>().lambda().eq(PowerUserEntity::getUserId, userEntityVo.getId()));
//        List<Long> powerIdList = powerUserEntities.stream().map(c -> {
//            return c.getPowerId();
//        }).collect(Collectors.toList());


        // 查询权限
        CompletableFuture<List<PowerEntry>> f1 = CompletableFuture.supplyAsync(() -> {

            return powerMapper.selectList(new QueryWrapper<PowerEntry>().lambda());
        },threadPoolExecutor);
        List<PowerEntry> powerEntryList = null;
        try {
            powerEntryList  = f1.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        userEntityPowerListVo.setPowerEntryList(powerEntryList);

        // 查询该用户
        CompletableFuture<UserEntity> f2 = CompletableFuture.supplyAsync(() -> {
            UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, id));
            return userEntity;
        });

        try {
            UserEntity userEntity = f2.get();
            userEntityPowerListVo.setId(userEntity.getId());
            //username
            userEntityPowerListVo.setUsername(userEntity.getUsername());
            //nickName
            userEntityPowerListVo.setNickName(userEntity.getNickName());
            //realName
            userEntityPowerListVo.setRealName(userEntity.getRealName());
            //email
            userEntityPowerListVo.setEmail(userEntity.getEmail());
            //idCard
            userEntityPowerListVo.setIdCard(userEntity.getIdCard());
            //headerImages
            userEntityPowerListVo.setHeaderImages(userEntity.getHeaderImages());
            //gender
            userEntityPowerListVo.setGender(userEntity.getGender());
            //birthday
            userEntityPowerListVo.setBirthday(userEntity.getBirthday());
            //city
            userEntityPowerListVo.setCity(userEntity.getCity());
            //remark
            userEntityPowerListVo.setRemark(userEntity.getRemark());
        } catch (Exception e) {
            e.printStackTrace();
        }
        CompletableFuture.allOf(f1,f2).join();

        return userEntityPowerListVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertUserPower(UserEntityPowerListVo userEntityPowerListVo) {
        UserEntity userEntity = new UserEntity();

        //username
        userEntity.setUsername(userEntityPowerListVo.getUsername());
        //nickName
        userEntity.setNickName(userEntityPowerListVo.getNickName());
        //realName
        userEntity.setRealName(userEntityPowerListVo.getRealName());
        //email
        userEntity.setEmail(userEntityPowerListVo.getEmail());
        //idCard
        userEntity.setIdCard(userEntityPowerListVo.getIdCard());
        //headerImages
        userEntity.setHeaderImages(userEntityPowerListVo.getHeaderImages());
        //gender
        userEntity.setGender(userEntityPowerListVo.getGender());
        //birthday
        userEntity.setBirthday(userEntityPowerListVo.getBirthday());
        //city
        userEntity.setCity(userEntityPowerListVo.getCity());
        //remark
        userEntity.setRemark(userEntityPowerListVo.getRemark());

        userMapper.update(userEntity,new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,userEntityPowerListVo.getId()));
        String ids = userEntityPowerListVo.getIds();
        PowerUserEntity powerUserEntity = new PowerUserEntity();

        if (ids.contains(",")){
            String[] split =ids.split(",");
            Arrays.stream(split).forEach(c ->{
                long l = Long.parseLong(c);
                List<PowerUserEntity> powerUserEntityList = powerUserMapper.selectList(new QueryWrapper<PowerUserEntity>().lambda().eq(PowerUserEntity::getPowerId, l));
                if (powerUserEntityList!=null){
                    powerUserEntityList.stream().forEach(item->{
                        powerUserEntity.setPowerId(item.getPowerId());
                        powerUserEntity.setUserId(userEntityPowerListVo.getId());
                        powerUserMapper.insert(powerUserEntity);
                    });
                }
            });
        }else {
            long l = Long.parseLong(ids);
            PowerUserEntity selectOne = powerUserMapper.selectOne(new QueryWrapper<PowerUserEntity>().lambda().eq(PowerUserEntity::getPowerId, l));
            if (selectOne!=null) {
                powerUserEntity.setPowerId(l);
                powerUserEntity.setUserId(userEntityPowerListVo.getId());
                powerUserMapper.insert(powerUserEntity);
            }
        }

        return Result.success("权限添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delUserPower(Long id) {
        baseMapper.delete(new QueryWrapper<PowerUserEntity>().lambda().eq(PowerUserEntity::getUserId,id));
        userMapper.delete(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,id));
        return Result.success("删除成功");
    }

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }

}
