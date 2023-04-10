package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.order.domain.OrderEntity;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
public interface OrderService extends IService<OrderEntity> {


    OrderEntity getOrder(Long userId, Long id);
}

