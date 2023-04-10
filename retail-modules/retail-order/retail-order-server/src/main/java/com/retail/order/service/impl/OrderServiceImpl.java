package com.retail.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.order.domain.OrderEntity;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public OrderEntity getOrder(Long userId, Long id) {
        List<OrderEntity> orderEntities = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda()
                .eq(OrderEntity::getId, id)
                .eq(OrderEntity::getUserId, userId));
        if (orderEntities==null||orderEntities.size()==0){
            return null;
        }
        OrderEntity  orderEntity   =orderEntities.get(0);
        return orderEntity;
    }
}
