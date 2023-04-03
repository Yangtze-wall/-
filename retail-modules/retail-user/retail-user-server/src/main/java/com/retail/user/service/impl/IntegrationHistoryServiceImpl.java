package com.retail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.retail.common.result.Result;
import com.retail.user.domain.request.UserIntegralAdd;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.IntegrationHistoryMapper;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.service.IntegrationHistoryService;

import javax.annotation.Resource;


@Service("integrationHistoryService")
public class IntegrationHistoryServiceImpl extends ServiceImpl<IntegrationHistoryMapper, IntegrationHistoryEntity> implements IntegrationHistoryService {

    @Resource
    private IntegrationHistoryMapper integrationHistoryMapper;

    @Override
    public Result add(UserIntegralAdd userIntegralAdd) {
        int update = integrationHistoryMapper.IntergralAdd(userIntegralAdd);
        if (update < 0) {
            return Result.error("积分增加失败");
        }
        //增加积分记录
        IntegrationHistoryEntity integrationHistoryEntity = new IntegrationHistoryEntity();
        integrationHistoryEntity.setUserId(userIntegralAdd.getUserId());
        integrationHistoryEntity.setCount(userIntegralAdd.getIntegral());
        integrationHistoryEntity.setCreateTime(new Date());
        integrationHistoryEntity.setSourceType(userIntegralAdd.getSourceType());
        integrationHistoryMapper.insert(integrationHistoryEntity);
        return Result.success("积分增加成功");
    }
}
