package com.retail.user.service.impl;

import com.retail.common.result.Result;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.IntegrationHistoryMapper;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.service.IntegrationHistoryService;


@Service("integrationHistoryService")
public class IntegrationHistoryServiceImpl extends ServiceImpl<IntegrationHistoryMapper, IntegrationHistoryEntity> implements IntegrationHistoryService {


    @Override
    public Result insert(IntegrationHistoryEntity integrationHistoryEntity) {

        baseMapper.insert(integrationHistoryEntity);
        return Result.success();
    }
}
