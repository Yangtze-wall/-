package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.order.domain.ExpressageEntity;


import java.util.List;
import java.util.Map;

/**
 * 快递公司
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:07:25
 */
public interface ExpressageService extends IService<ExpressageEntity> {


    Result<List<ExpressageEntity>> getExpressageList();
}

