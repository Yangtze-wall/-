package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.domain.request.UserIntegralAdd;

import java.util.Map;

/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
public interface IntegrationHistoryService extends IService<IntegrationHistoryEntity> {

    /**
     * 用户积分添加
     * @param userIntegralAdd
     * @return
     */
    Result add(UserIntegralAdd userIntegralAdd);
}

