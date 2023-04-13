package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;


import java.util.Map;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
public interface OrderService extends IService<OrderEntity> {


    Result<OrderEntity>  createSkeillOrder(Long spuId);

    Result updateOrderAddress(OrderEntity orderEntity);

}

