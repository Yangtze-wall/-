package com.retail.order.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.order.config.OrderRabbitMq;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.mapper.PaymentMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

/**
 * @author JobConfig
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.job
 * @date: 2023-04-07  09:38
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Configuration
public class JobConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentMapper paymentMapper;

    /**
     * 定时5分钟
     */
    @XxlJob("orderJobConpun")
    public void orderJobConpun(){
        // 查询订单的状态 是否支付 完成
        List<OrderEntity> orderEntityList = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getStatus, 0));
        // mq 异步
        rabbitTemplate.convertAndSend(OrderRabbitMq.DEAD_EXCHANGE, OrderRabbitMq.QUEUE_NAME, JSON.toJSONString(orderEntityList), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setMessageId(UUID.randomUUID().toString().replaceAll("-",""));
                return message;
            }
        });
    }

    // 订单信息  补偿     调用支付宝接口 查询订单编号支付的情况根据 状态  TRADE_SUCCESS   不是支付失败
    @XxlJob("alipayJobStatus")
    public void alipayJobStatus(){
        List<OrderEntity> orderEntityList = orderMapper.selectList(null);
        orderEntityList.stream().forEach(c->{
            PayMentEntity payMentEntity = paymentMapper.selectOne(new QueryWrapper<PayMentEntity>().lambda().eq(PayMentEntity::getOrderSn, c.getOrderSn()));
            // 判断状态是否成功 失败 修改订单表状态3 支付失败
            if (payMentEntity==null || !payMentEntity.getPaymentStatus().equals("TRADE_SUCCESS")){
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(c.getOrderSn());
                orderEntity.setStatus(3);
                orderMapper.update(orderEntity,new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn,c.getOrderSn()));
            }
        });
    }


}
