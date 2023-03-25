package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.user.domain.PowerEntity;
import com.retail.user.domain.UserEntity;
import com.retail.user.domain.vo.RequestPower;


/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author retail
 * @since 2023-03-23
 */
public interface PowerService extends IService<PowerEntity> {

    Result<PageResult<UserEntity>> pageList(RequestPower requestPower);

    void updateStatus(Long id);
}
