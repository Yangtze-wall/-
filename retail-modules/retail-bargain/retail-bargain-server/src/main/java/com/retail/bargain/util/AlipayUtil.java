package com.retail.bargain.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.retail.bargain.domain.request.PayRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lenovo
 * @Package_name com.example.demo
 * @Description TODO
 * @createTime 2023/3/27 9:45
 */
@Component
@Log4j2
public class AlipayUtil {
    @Resource
    private HttpServletResponse httpResponse;
    @Value("${aliPay.appId}")
    private String appId;
    @Value("${aliPay.url}")
    private String url;
    @Value("${aliPay.privateKey}")
    private String privateKey;
    @Value("${aliPay.publicKey}")
    private String publicKey;
    @Value("${aliPay.notifyUrl}")
    private String notifyUrl;
    @Value("${aliPay.returnUrl}")
    private String returnUrl;

    /**
     * 支付页面
     */
    public String aliPay(PayRequest pay) {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(notifyUrl);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(returnUrl);
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", pay.getOutTradeNo());
        //支付金额，最小值0.01元
        bizContent.put("total_amount", pay.getTotalAmount());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", pay.getSubject());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        request.setBizContent(bizContent.toString());
        String payUrl = null;
        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            response = alipayClient.pageExecute(request);

            if (response.isSuccess()) {
//                httpResponse.setContentType("text/html;charset=utf-8");
//                httpResponse.getWriter().write(response.getBody());
//                httpResponse.getWriter().flush();
//                httpResponse.getWriter().close();
                payUrl = response.getBody();
                log.info("调用成功");
            } else {
                log.info("调用失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payUrl;
    }

    /**
     * 交易查询
     */
    public void query(String out_trade_no) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);

        if (response.isSuccess()) {
            log.info("调用成功");
        } else {
            log.info("调用失败");
        }
    }

//    /**
//     * 回调
//     */
//    public String payNotify(HttpServletRequest request) throws Exception {
//        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
//            System.out.println("=========支付宝异步回调========");
//
//            Map<String, String> params = new HashMap<>();
//            Map<String, String[]> requestParams = request.getParameterMap();
//            for (String name : requestParams.keySet()) {
//                params.put(name, request.getParameter(name));
//            }
//
//            System.out.println(params.get("out_trade_no"));
//            System.out.println(params.get("gmt_payment"));
//            System.out.println(params.get("trade_no"));
//
//            String sign = params.get("sign");
//            String content = AlipaySignature.getSignCheckContentV1(params);
//            // 验证签名
//            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, publicKey, "UTF-8");
//            // 支付宝验签
//            if (checkSignature) {
//                // 验签通过
//                log.info("交易名称: " + params.get("subject"));
//                log.info("交易状态: " + params.get("trade_status"));
//                log.info("支付宝交易凭证号: " + params.get("trade_no"));
//                log.info("商户订单号: " + params.get("out_trade_no"));
//                log.info("交易金额: " + params.get("total_amount"));
//                log.info("买家在支付宝唯一id: " + params.get("buyer_id"));
//                log.info("买家付款时间: " + params.get("gmt_payment"));
//                log.info("买家付款金额: " + params.get("buyer_pay_amount"));
//            }
//        }
//        return "success";
//    }


}
