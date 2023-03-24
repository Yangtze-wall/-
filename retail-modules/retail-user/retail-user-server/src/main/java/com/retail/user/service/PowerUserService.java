package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.user.domain.vo.UserEntityPowerListVo;
import com.retail.user.domain.vo.UserEntryPowerVo;
import com.retail.user.domain.PowerUserEntity;

import java.util.List;

/**
 * 权限  用户 中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 19:31:59
 */
public interface PowerUserService extends IService<PowerUserEntity> {


    List<UserEntryPowerVo> getPowerUserEntryList();

    UserEntityPowerListVo findByIdUserPower(Long id);

    Result insertUserPower(UserEntityPowerListVo userEntityPowerListVo);

    Result delUserPower(Long id);
}

