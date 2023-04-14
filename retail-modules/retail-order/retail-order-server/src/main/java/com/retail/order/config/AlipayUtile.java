package com.retail.order.config;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import com.retail.order.domain.AlipayBean;
import com.retail.order.domain.OrderEntity;
import com.retail.order.service.OrderService;
import com.retail.order.service.PaymentService;
import com.retail.order.utils.AlipayConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AlipayUtile {


    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    public Result<String> pay(AlipayBean alipayBean) throws AlipayApiException {
        //根据订单号查询订单信息是否存在
        OrderEntity orderEntity = orderService.getOne(new QueryWrapper<OrderEntity>().lambda()
                .eq(OrderEntity::getOrderSn, alipayBean.getOut_trade_no()));
        if (orderEntity==null){
            return Result.error("订单不存在");
        }
        //防止重复支付(状态(1.待支付 2.支付成功 3.过期 4 正在支付 稍等))
        if (orderEntity.getStatus().intValue()!=1){
            return Result.error("订单已支付");
        }


        String serverUrl = alipayConfig.getGatewayUrl();
        String appId = alipayConfig.getAppId();
        String privateKey = alipayConfig.getPrivateKey();
        String format = "json";
        String charset = alipayConfig.getCharset();
        String alipayPublicKey = alipayConfig.getPublicKey();
        String signType = alipayConfig.getSignType();
        String returnUrl = alipayConfig.getReturnUrl();
        String notifyUrl = alipayConfig.getNotifyUrl();
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);

        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        log.info(notifyUrl);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        // 3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        // 返回付款信息
        return Result.success(result);

    }
}

