package com.retail.order.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.retail.common.result.Result;
import com.retail.order.config.AliPayConfig;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.service.AliPayService;
import com.retail.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private PaymentService paymentService;

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

        //


        if (signVerified) {


            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + params.get("trade_status"));
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));


            PayMentEntity payMentEntity = new PayMentEntity();
            //添加支付表记录
            String orderSn = params.get("out_trade_no");
            payMentEntity.setAlipayTradeNo(params.get("trade_no"));
            payMentEntity.setTotalAmount(params.get("total_amount"));
            payMentEntity.setSubject(params.get("subject"));
            payMentEntity.setOutTradeNo(params.get("out_trade_no"));
            payMentEntity.setCreateTime(params.get("gmt_payment"));
            payMentEntity.setPaymentStatus(params.get("trade_status"));
            payMentEntity.setOrderSn(orderSn);
            paymentService.createColonelOrderPay(payMentEntity);
            //修改订单支付状态   添加结账时间


            //  支付成功后给用户加积分，调用用户加积分接口


            return "success";
        } else {
            System.out.println("签名验证失败...");

            return "error";
        }



    }

}
