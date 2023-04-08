package com.retail.bargain.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.bargain.config.DelayRabbitMq;
import com.retail.bargain.domain.BargainEntity;
import com.retail.bargain.mapper.BargainMapper;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author JobConfig
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.job
 * @date: 2023-03-31  18:42
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Component
@Log4j2
public class JobConfig {
    @Autowired
    private BargainMapper bargainMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @XxlJob("bargainDateUpdate")
    public void bargainDateUpdate(){
        // 判断 状态  时间   砍价状态 0.正在砍价
        List<BargainEntity> bargainEntityList =
                bargainMapper
                        .selectList(
                                new QueryWrapper<BargainEntity>().lambda()
                                        .eq(BargainEntity::getStatus,0)
                                        .eq(BargainEntity::getBargainStatus,0)
                        );
        // mq 异步
        rabbitTemplate.convertAndSend(DelayRabbitMq.DEAD_EXCHANGE, DelayRabbitMq.QUEUE_NAME, JSON.toJSONString(bargainEntityList), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setMessageId(UUID.randomUUID().toString().replaceAll("-",""));
                return message;
            }
        });


    }
}
