package com.retail.order.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.order.config.OrderRabbitMq;
import com.retail.order.domain.OrderEntity;
import com.retail.order.mapper.OrderMapper;
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

}
