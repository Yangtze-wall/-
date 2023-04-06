package com.retail.bargain.service.impl;

import com.retail.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.bargain.mapper.SeckillMapper;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.service.SeckillService;


@Service("seckillService")
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillEntity> implements SeckillService {


    @Override
    public Result<List<SeckillEntity>> select() {
        List<SeckillEntity> seckillEntityList = baseMapper.selectList(null);
        return Result.success(seckillEntityList);
    }
}
