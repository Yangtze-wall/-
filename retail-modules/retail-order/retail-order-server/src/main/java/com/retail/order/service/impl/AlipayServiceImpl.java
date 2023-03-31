package com.retail.order.service.impl;

import ch.qos.logback.core.util.EnvUtil;
import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.retail.order.service.AliPayService;
import com.retail.order.utils.AliPayConfig;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ProjectName:    limeibo-yuekao-3.17
 * @Package:        com.bwie.service.imp
 * @ClassName:      AlipayServiceImpl
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/28 21:22
 * @Version:    1.0
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AliPayService {


    @Override
    public void aliPay(HttpServletResponse response, HttpServletRequest request,String total_amount)  {
//       EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        response.setContentType("text/html;charset=utf-8");


        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        //aliPayRequest.setReturnUrl(AliPayConfig.return_url);
        aliPayRequest.setNotifyUrl(AliPayConfig.notify_url);

        String str = RandomUtil.randomNumbers(13);
        System.out.println(str);
        //商户订单号，后台可以写一个工具类生成一个订单号，必填
        String order_number = new String(total_amount);
        //付款金额，从前台获取，必填

        //订单名称，必填
        String subject = new String("臭宝刘薇");
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //输出
        out.println(result);

       // log.info("返回结果={}",result);


    }
}
