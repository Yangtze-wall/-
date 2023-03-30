package com.retail.order.service.impl;

import com.retail.common.result.Result;
import com.retail.order.mapper.ExpressageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



import com.retail.order.domain.ExpressageEntity;
import com.retail.order.service.ExpressageService;


@Service("expressageService")
public class ExpressageServiceImpl extends ServiceImpl<ExpressageMapper, ExpressageEntity> implements ExpressageService {


    @Override
    public Result<List<ExpressageEntity>> getExpressageList() {
        List<ExpressageEntity> expressageEntityList=baseMapper.selectList(new QueryWrapper<ExpressageEntity>());
        return Result.success(expressageEntityList);
    }
}
