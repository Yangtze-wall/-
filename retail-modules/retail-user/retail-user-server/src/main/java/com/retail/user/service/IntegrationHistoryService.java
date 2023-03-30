package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.user.domain.IntegrationHistoryEntity;

import java.util.Map;

/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
public interface IntegrationHistoryService extends IService<IntegrationHistoryEntity> {


    Result insert(IntegrationHistoryEntity integrationHistoryEntity);
}

