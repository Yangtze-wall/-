package com.retail.order.controller;


import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import com.retail.order.config.AlipayUtile;
import com.retail.order.domain.AlipayBean;
import com.retail.order.domain.OrderEntity;
import com.retail.order.mapper.OrderMapper;

import com.retail.order.utils.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
@RequestMapping("order/payment")
public class AlipayController {



    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AlipayUtile alipayUtile;

    @Autowired
    private AlipayConfig alipayConfig;

    //  order/payment/pay
    @RequestMapping("/pay")
    public Result<String> payMent(@RequestBody OrderEntity orderEntity) throws AlipayApiException {

        BigDecimal payAmount = orderEntity.getPayAmount();
        String total_amount = payAmount.toString();
        //订单名称，必填
        String subject = new String("支付支付");
        AlipayBean alipayBean = new AlipayBean();
        String str = RandomUtil.randomNumbers(13);
        System.out.println(str);
        String order_number = new String(str);
        alipayBean.setOut_trade_no(order_number);
        alipayBean.setTotal_amount(total_amount);
        alipayBean.setSubject(subject);
        alipayBean.setBody("sss");
        String pay = alipayUtile.pay(alipayBean);
        return Result.success(pay);
    }

    // order/payment/payed/notify
    @PostMapping("/payed/notify")
    public String handleAlipayed(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
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
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info(params.toString());
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(),
                alipayConfig.getCharset(), alipayConfig.getSignType());

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
            return "成功";
        } else {
            System.out.println("签名验证失败...");
            return "失败";
        }
    }
    /**
    @RequestMapping("/huidiao")
    public void huidiao(String orderSn){
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getPublicKey(),
                alipayConfig.getSignType());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderSn);// 订单编号
        bizContent.put("trade_no", "2023032922001453850501915130"); //  2088721010611155
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
    */




}
