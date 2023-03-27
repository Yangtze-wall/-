package com.retail.user.service.impl;

import com.retail.common.domain.vo.AddressVo;
import com.retail.user.domain.UserAddressEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.user.mapper.UserAddressMapper;
import com.retail.user.service.UserAddressService;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {


    @Override
    public List<AddressVo> selectOrderAddress(Long id) {
        List<UserAddressEntity> userAddressEntityList = this.baseMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, id));

        List<AddressVo> collect = userAddressEntityList.stream().map(c -> {
            AddressVo addressVo = new AddressVo();
            BeanUtils.copyProperties(c, addressVo);
            return addressVo;
        }).collect(Collectors.toList());


        return collect;
    }
}
