package com.retail.order.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.config.AliPayConfig;
import com.retail.order.config.AliPayRabbitMq;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.feign.UserFeignService;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.mapper.PaymentMapper;
import com.retail.order.service.AliPayService;
import com.retail.order.service.PaymentService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author AliPayController
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.controller
 * @date: 2023-04-06  14:51
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/alipay")
public class AliPayController {
    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;



    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }

    /**
     * 调用支付宝接口
     * @param
     * @param
     * @param
     */
    //  /alipay/pay
    @PostMapping("/pay")
    public Result<String> payMent(@RequestBody OrderEntity order) {
        Result<String> stringResult = aliPayService.aliPay(order);
        return stringResult;
    }
    //  /alipay/payed/notify
    @PostMapping("/payed/notify")
    public String handleAlipayed(HttpServletRequest request) throws Exception, UnsupportedEncodingException {
        //阿里验签方法
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key,
                AliPayConfig.charset, AliPayConfig.sign_type);

        if (signVerified) {
            String orderSn = params.get("out_trade_no");
            // 修改状态 为正在支付 4
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setStatus(4);
            orderEntity.setOrderSn(orderSn);
            orderMapper.update(orderEntity,new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn,orderSn));

            String trade_status = params.get("trade_status");
            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + trade_status);
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));


            PayMentEntity selectOne = paymentMapper.selectOne(new QueryWrapper<PayMentEntity>().lambda().eq(PayMentEntity::getOrderSn, orderSn));
            if (selectOne!= null){
                return "已支付过";
            }
//            // 正在支付
//            orderEntity.setStatus(1);
//            orderEntity.setOrderSn(orderSn);
//            orderMapper.update(orderEntity,new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn,orderSn));
            // 判断 交易状态 是否是成功的
            if (!trade_status.equals("TRADE_SUCCESS")){
                return "支付失败";
            }


            PayMentEntity payMentEntity = new PayMentEntity();
            //添加支付表记录
            String gmt_payment = params.get("gmt_payment");
            payMentEntity.setAlipayTradeNo(params.get("trade_no"));
            payMentEntity.setTotalAmount(params.get("total_amount"));
            payMentEntity.setSubject(params.get("subject"));
            payMentEntity.setOutTradeNo(params.get("out_trade_no"));
            payMentEntity.setCreateTime(gmt_payment);
            payMentEntity.setPaymentStatus(params.get("trade_status"));
            payMentEntity.setOrderSn(orderSn);
            int insert = paymentMapper.insert(payMentEntity);
            if (insert>0){
                // String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor
                rabbitTemplate.convertAndSend(AliPayRabbitMq.DEAD_EXCHANGE, AliPayRabbitMq.QUEUE_NAME, JSON.toJSONString(payMentEntity), new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setMessageId(UUID.randomUUID().toString().replaceAll("-",""));
                        return message;
                    }
                });
            }




            return "success";
        } else {
            System.out.println("签名验证失败...");
            return "error";
        }



    }

}
