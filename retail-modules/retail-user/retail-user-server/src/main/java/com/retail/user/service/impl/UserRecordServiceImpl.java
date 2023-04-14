package com.retail.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserRecordEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserRecordMapper;
import com.retail.user.service.UserRecordService;


@Service("userRecordService")
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecordEntity> implements UserRecordService {


    @Override
    public Result insertRecord(UserRecordEntityVo userRecordEntityVo) {
        UserRecordEntity userRecordEntity = new UserRecordEntity();
        BeanUtil.copyProperties(userRecordEntityVo,userRecordEntity);
        this.baseMapper.insert(userRecordEntity);
        return Result.success();
    }
}
