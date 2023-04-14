package com.retail.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import com.retail.order.config.AliPayConfig;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.mapper.PaymentMapper;
import com.retail.order.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author AliPayServiceImpl
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.service.impl
 * @date: 2023-04-06  14:48
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Result<String> aliPay(OrderEntity order)  {
        // 通过订单编号 查询支付表 是否支付
        PayMentEntity payMentEntity = paymentMapper.selectOne(new QueryWrapper<PayMentEntity>().lambda().eq(PayMentEntity::getOrderSn, order.getOrderSn()));
        if (payMentEntity != null){
            return Result.error("该订单已支付");
        }
        // 判断订单 状态
        OrderEntity selectOne = orderMapper.selectOne(new QueryWrapper<OrderEntity>().lambda().eq(OrderEntity::getOrderSn, order.getOrderSn()));
        // 订单状态 4 正在支付 稍等
        if (selectOne.getStatus().equals(2)){
            return Result.error("该订单已支付");
        }
            // 状态 2 支付成功
        if (selectOne.getStatus().equals(4)){
            return Result.error("正在支付稍等");
        }


        // EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        response.setContentType("text/html;charset=utf-8");
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AliPayConfig.return_url);
        aliPayRequest.setNotifyUrl(AliPayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = new String(order.getOrderSn());

        //订单名称，必填
        String subject = new String("操操操操操");
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + order.getPayAmount() + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(result);

        return Result.success(result);
    }
}
