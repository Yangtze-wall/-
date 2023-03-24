package com.retail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import com.retail.user.config.ThreadConfig;
import com.retail.user.domain.vo.UserEntityPowerListVo;
import com.retail.user.domain.vo.UserEntryPowerVo;
import com.retail.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.PowerUserMapper;
import com.retail.user.domain.PowerUserEntity;
import com.retail.user.service.PowerUserService;


@Service("powerUserService")
public class PowerUserServiceImpl extends ServiceImpl<PowerUserMapper, PowerUserEntity> implements PowerUserService {

    @Autowired
    private PowerUserMapper powerUserMapper;

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

        CompletableFuture<List<PowerUserEntity>> f1 = CompletableFuture.supplyAsync(() -> {
            return baseMapper.selectList(new QueryWrapper<PowerUserEntity>().lambda());
        },threadPoolExecutor);
        List<PowerUserEntity> powerUserEntityList = null;
        try {
            powerUserEntityList = f1.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        userEntityPowerListVo.setPowerUserEntityList(powerUserEntityList);




        return null;
    }

    @Override
    public Result insertUserPower(UserEntityPowerListVo userEntityPowerListVo) {
        return null;
    }

    @Override
    public Result delUserPower(Long id) {
        return null;
    }
}
