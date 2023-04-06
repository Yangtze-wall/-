package com.retail.order.service;

import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;

/**
 * @author AliPayService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.service
 * @date: 2023-04-06  14:50
 * @Created by:  12871
 * @Description:
 * @Version:
 */
public interface AliPayService {
    public Result<String> aliPay(OrderEntity order);

}
