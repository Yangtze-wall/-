package com.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.retail.order.domain.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    void createOrder(OrderEntity orderEntity);

}
