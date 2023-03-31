package com.retail.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

import com.retail.order.config.PayVo;
import com.retail.order.service.AliPayService;
import com.retail.order.service.OrderService;
import com.retail.order.utils.AliPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ProjectName:    limeibo-yuekao-3.17
 * @Package:        com.bwie.config
 * @ClassName:      AlipayController
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/28 15:15
 * @Version:    1.0
 */
@Slf4j
@RestController
@RequestMapping("payment")
public class AlipayController {


    @Autowired
    private AliPayService alipayService;

    @Autowired
    private OrderService orderService;



    @RequestMapping("/pay")
    public void payMent(HttpServletResponse response, HttpServletRequest request,String total_amount) {

        try {

            alipayService.aliPay(response, request,total_amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/payed/notify")
    public String handleAlipayed(PayVo vo, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        Map<String, String[]> map = request.getParameterMap();

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


            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + params.get("trade_status"));
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));



            System.out.println("签名验证成功...");
            String result = orderService.handlePayResult(vo);
            return "success";
        } else {
            System.out.println("签名验证失败...");
            return "error";
        }



    }

    @RequestMapping("/huidiao")
    public void huidiao(){

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AliPayConfig.app_id,AliPayConfig.merchant_private_key,"json",AliPayConfig.charset,AliPayConfig.alipay_public_key,AliPayConfig.sign_type);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "8307677498180");
        bizContent.put("trade_no", "2023032922001453850501915130");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }




}
