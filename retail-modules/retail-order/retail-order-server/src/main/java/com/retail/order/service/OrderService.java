package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.request.OrderRequest;
import com.retail.order.vo.OrderEntityVo;

import java.util.List;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
public interface OrderService extends IService<OrderEntity> {


//    Result createSkeillOrder(OrderEntity orderEntity);
//
//    Result updateOrderAddress(OrderEntity orderEntity);

    /**
     * 订单列表
     * @return
     */
    List<OrderEntityVo> orderList(OrderRequest request);
}

