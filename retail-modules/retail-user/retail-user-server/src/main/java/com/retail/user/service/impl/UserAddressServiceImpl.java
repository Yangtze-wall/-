package com.retail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.user.domain.UserAddressEntity;
import com.retail.user.mapper.UserAddressMapper;
import com.retail.user.service.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressEntity> selectUserAddressEntity(Long id) {
        List<UserAddressEntity> userAddressEntities = userAddressMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, id));
        return userAddressEntities;
    }

    @Override
    public UserAddressEntity findById(Long id) {

        UserAddressEntity userAddressEntity = this.userAddressMapper.selectById(id);
        return userAddressEntity;
    }

    @Override
    public void insert(UserAddressEntity params) {
        params.setCreateTime(new Date());
        this.userAddressMapper.insert(params);
    }

    @Override
    public void delete(Long id) {
        this.userAddressMapper.deleteById(id);
    }

    @Override
    public void updateAddress(UserAddressEntity params) {
        params.setUpdateTime(new Date());
        this.userAddressMapper.updateById(params);
    }

    @Override
    public void upateStatus(Long id, Long userId) {
        userAddressMapper.update1(userId);
        userAddressMapper.update2(id);
    }

    @Override
    public List<UserAddressEntity> findUserAddressEntity(String userId) {
        List<UserAddressEntity> userAddressEntities = userAddressMapper.selectList(new QueryWrapper<UserAddressEntity>().lambda().eq(UserAddressEntity::getUserId, userId));
        return userAddressEntities;
    }
}
