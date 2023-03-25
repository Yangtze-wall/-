package com.retail.auth.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.retail.auth.service.SmsService;
import com.retail.auth.vo.SmsParamVo;
import com.retail.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.listener
 * @Classname: SmsListener
 * @CreateTime: 2023-03-24  19:39
 * @Created by: 喵喵
 * @Description: 监听
 * @Version:
 */
@Component
@Log4j2
public class SmsListener {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String>  redisTemplate;

    @RabbitListener(queues = "retail.queue.sms")
    public void smsSendListener(String msg, Channel channel, Message message) throws IOException {
        //消息唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            SmsParamVo smsParamVo = JSON.parseObject(msg, SmsParamVo.class);
            //解决重复消费问题
            String msgId = redisTemplate.opsForValue().get("sms_msg_" + smsParamVo.getMsgId());
            if (StringUtils.isNotBlank(msgId)){
                //确认签收，删除队列消息
                channel.basicAck(deliveryTag,true);
                return;
            }
            smsService.sendSms(smsParamVo.getPhone(),smsParamVo.getCode());
            //如果消费成功 存入 redis 里面6小时
            redisTemplate.opsForValue().set("sms_msg_"+smsParamVo.getMsgId(),smsParamVo.getPhone(),
                    6, TimeUnit.HOURS);
            //确认签收，删除队列消息
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            //回滚 消息重回队列
            // (long deliveryTag, boolean multiple(是否应用多消息), boolean requeue
            channel.basicNack(deliveryTag,false,true);
            e.printStackTrace();
        }
        log.info("短信发送成功");
    }

}
