package com.retail.user.service.impl;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.user.domain.UserRecordEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserRecordMapper;
import com.retail.user.service.UserRecordService;


@Service("userRecordService")
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecordEntity> implements UserRecordService {


    @Autowired
    private UserRecordMapper userRecordMapper;


    @Override
    public List<CommissionVo> selectCommission(UserEntityVo userEntityVo) {

        UserRecordEntity userRecordEntity = new UserRecordEntity();
        userRecordEntity.setUserId(userEntityVo.getId());
        userRecordEntity.setRechargeType(4);

        List<UserRecordEntity> userRecordEntityList = userRecordMapper.selectCommission(userRecordEntity);


        List<CommissionVo> collect = userRecordEntityList.stream().map(iten -> {
            CommissionVo commissionVos = new CommissionVo();
            BeanUtils.copyProperties(iten, commissionVos);

            return commissionVos;
        }).collect(Collectors.toList());


        return collect;
    }




}
