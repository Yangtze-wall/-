package com.retail.order.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.retail.order.domain.RefundEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lenovo
 * @Package_name com.example.demo
 * @Description TODO
 * @createTime 2023/3/27 9:45
 */
@Component
public class AlipayUtil {
    @Value("${alipay.appid}")
    private String appId;
    @Value("${alipay.url}")
    private String url;
    @Value("${alipay.privateKey}")
    private String privateKey;
    @Value("${alipay.publicKey}")
    private String publicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    public String alipay(String id, String price, String title) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(notifyUrl);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(returnUrl);
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", id);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", price);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", title);
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");


        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        String from = null;
        try {
            response = alipayClient.pageExecute(request);
            from = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return from;
    }

    public String query(String out_trade_no) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        String query = null;
        try {
            response = alipayClient.execute(request);
            query = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return query;
    }

    public String refund(RefundEntity refundEntity) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", refundEntity.getTradeNo());
        bizContent.put("refund_amount", refundEntity.getRefundAmount());
        bizContent.put("out_request_no", refundEntity.getOutTradeNo());

        //// 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = null;
        String refund = null;
        try {
            response = alipayClient.execute(request);
            refund = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return refund;
    }
}
