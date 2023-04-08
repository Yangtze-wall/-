package com.retail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.domain.vo.SignTimeSearchVo;

import java.util.List;
import java.util.Map;

/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
public interface IntegrationHistoryService extends IService<IntegrationHistoryEntity> {


    Result insertSign(IntegrationHistoryEntity integrationHistoryEntity);



    Map<String, Object> doSign(Long id, String dateStr);

}

