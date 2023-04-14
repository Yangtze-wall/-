package com.retail.order.config;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.feign.UserFeignService;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.AliPayService;
import com.retail.order.service.PaymentService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Lenovo
 * 延迟队列
 */
@Configuration
@Log4j2
public class AliPayRabbitMq {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ShopFeignService shopFeignService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private HttpServletRequest request;


    /**
     * 业务队列
     */
    public static final String QUEUE_NAME = "alipay_queue";
    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "alipay_dead_queue";
    /**
     *
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "alipay_exchange";
    /**
     * 死信路由
     */
    public static final String DEAD_QUEUE_KEY = "alipay_key";


    /**
     * 实例化业务队列
     */
    @Bean
    public Queue createAliPayQueue(){
        HashMap<String, Object> map = new HashMap<>();
        //绑定死信交换机
        map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //路由
        map.put("x-dead-letter-routing-key", DEAD_QUEUE_KEY);
        //设置过期时间
        map.put("x-message-ttl", 6000);
        return new Queue(QUEUE_NAME,true, false, false,map);
    }
    /**
     * 实例化死信队列
     */
    @Bean
    public Queue createAliPayDead(){
        Queue queue = new Queue(DEAD_QUEUE);
        return queue;
    }

    /**
     * 实例化死信交换机
     */
    @Bean
    public Exchange createAliPayExchange(){
        DirectExchange directExchange = new DirectExchange(DEAD_EXCHANGE);
        return directExchange;
    }


    /**
     * 死信队列绑定死信交换机
     */
    @Bean
    public Binding createAliPayBinding(){
        Binding binding = new Binding(DEAD_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE, DEAD_QUEUE, null);
        return binding;
    }
    @Bean
    public Binding aliPayBinding(){
        Binding binding = new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, DEAD_EXCHANGE, QUEUE_NAME, null);
        return binding;
    }



    /**
     *  异步订单支付成功 添加记录
     *  修改订单支付状态
     *  积分记录表 添加
     *  修改库存
     * 账户记录流水表
     * @param json
     * @param channel
     * @param message
     */
    @RabbitListener(queues = DEAD_QUEUE)
    @GlobalTransactional(rollbackFor = Exception.class)
    public void  queueAliPayDead(String json, Channel channel, Message message){
        String messageId = message.getMessageProperties().getMessageId();
//        Long add = redisTemplate.opsForSet().add(messageId);
        try {
            PayMentEntity payMentEntity = JSON.parseObject(json, PayMentEntity.class);
            log.info(payMentEntity.toString());
            log.info("异步支付");
                //修改订单支付状态   添加结账时间
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setStatus(2);
                orderEntity.setRealityTime(new Date());
                orderMapper.update(orderEntity,new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn,payMentEntity.getOutTradeNo()));
                OrderEntity entity = orderMapper.selectOne(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn, payMentEntity.getOutTradeNo()));
            //  支付成功后给用户加积分，调用用户加积分接口
                UserEntityVo userEntityVo = new UserEntityVo();
                Result<UserEntityVo> byIdUser = userFeignService.findByIdUser(entity.getUserId());
                UserEntityVo userData = byIdUser.getData();
                int i = RandomUtil.randomInt(10,100);
                userEntityVo.setIntegration(userData.getIntegration()+i);
                userEntityVo.setId(entity.getUserId());
                userFeignService.updateIntegration(userEntityVo);
                // 积分记录表 添加
                IntegrationHistoryEntityVo integrationHistoryEntityVo = new IntegrationHistoryEntityVo();
                integrationHistoryEntityVo.setCreateTime(new Date());
                integrationHistoryEntityVo.setCount(i);
                integrationHistoryEntityVo.setRemark("支付添加积分");
                integrationHistoryEntityVo.setUserId(entity.getUserId());
                integrationHistoryEntityVo.setSourceType(2);
                userFeignService.integrationHistoryInsert(integrationHistoryEntityVo);
                // 修改库存  通过订单编号查询 spuID 修改库存   购买数量为 1
                Long spuId = entity.getSpuId();
                InventoryEntityVo inventoryEntityVo = shopFeignService.findByInventoryEntity(spuId).getData();
                InventoryEntityVo entityVo = new InventoryEntityVo();
                entityVo.setId(inventoryEntityVo.getId());
                entityVo.setSpuId(inventoryEntityVo.getSpuId());
                entityVo.setInventoryCount(inventoryEntityVo.getInventoryCount()-1);
                entityVo.setInventorySellCount(inventoryEntityVo.getInventoryLock());
                entityVo.setInventoryLock(0);
                shopFeignService.updateInventoryLock(entityVo);
                // 账户记录流水表
                UserRecordEntityVo userRecordEntityVo = new UserRecordEntityVo();
                //userId
                userRecordEntityVo.setUserId(entity.getUserId());
                //orderSn
                userRecordEntityVo.setOrderSn(payMentEntity.getOutTradeNo());
                //price
                userRecordEntityVo.setPrice(new BigDecimal(payMentEntity.getTotalAmount()));
                //rechargeType
                userRecordEntityVo.setRechargeType(3);
                //createTime
                userRecordEntityVo.setCreateTime(new Date());
                //describe
                userRecordEntityVo.setRemark("支付记录");
                userFeignService.userRecordInsert(userRecordEntityVo);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
