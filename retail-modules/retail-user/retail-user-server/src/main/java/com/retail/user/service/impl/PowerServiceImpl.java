package com.retail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.user.domain.PowerEntity;
import com.retail.user.domain.UserEntity;
import com.retail.user.domain.vo.RequestPower;
import com.retail.user.mapper.PowerMapper;
import com.retail.user.mapper.UserMapper;
import com.retail.user.service.PowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author retail
 * @since 2023-03-23
 */
@Service
public class PowerServiceImpl extends ServiceImpl<PowerMapper, PowerEntity> implements PowerService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Result<PageResult<UserEntity>>  pageList(RequestPower requestPower) {
        PageHelper.startPage(requestPower.getPageNum(), requestPower.getPageSize());
        List<UserEntity> list =userMapper.list(requestPower.getRealName());
        PageInfo<UserEntity> userEntityPageInfo = new PageInfo<>(list);
        Result<PageResult<UserEntity>> userEntityPageResult = PageResult.toResult(userEntityPageInfo.getTotal(), list);
        return userEntityPageResult;
    }

    @Override
    public void updateStatus(Long id) {
        userMapper.updateStatus(id);
    }
}
