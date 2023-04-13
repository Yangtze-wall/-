package com.retail.order.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.remote.BargainFeign;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.config.ApiData;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.RefundEntity;
import com.retail.order.service.OrderService;
import com.retail.order.util.AlipayUtil;
import com.retail.shop.domain.SkuEntity;
import com.retail.shop.remote.ShopFeign;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:55
 */
@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
    @Autowired
    private AlipayClient alipayClient;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;
    private static final String CHARSET="UTF-8";
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String ,String >redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private BargainFeign bargainFeign;
    @GetMapping("pay/{userId}/{id}")
    public String returnUrl(HttpServletResponse httpResponse, @PathVariable("userId") Long userId, @PathVariable("id") Long id) throws AlipayApiException, IOException {
        OrderEntity orderEntity = orderService.getOrder(userId,id);
        if (orderEntity!=null){
            throw new BizException(401,"你已经抢购了");
        }
        SeckillEntity seckillEntity = bargainFeign.show(id);
        if (seckillEntity.getSeckillResidueCount()==0){
            throw new BizException(401,"商品已卖完");
        }
        if (System.currentTimeMillis()>seckillEntity.getSeckillEndTime().getTime()) {
            throw new BizException(401, "活动时间已过");
        }
//        String token = request.getHeader("token");
//        String userKey = JwtUtils.getUserKey(token);
//        String s1 = redisTemplate.opsForValue().get(JwtConstants.USER_KEY + userKey);
//        UserEntityVo userEntityVo = JSON.parseObject(s1, UserEntityVo   .class);
//        log.info(JSON.toJSONString(userEntityVo));
        //        AlipayClient alipayClient = new DefaultAlipayClient("https:          //openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(notifyUrl);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(returnUrl);
/******必传参数******/
        JSONObject bizContent = new JSONObject();
        String s = IdUtil.getSnowflake(20, 1).nextIdStr();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", s);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", seckillEntity.getSeckillPrice());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", orderEntity.getName());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
///******可选参数******/
        //
//        bizContent.put("time_expire", new Date());
//        //          // 商品明细信息，按需传入
//        JSONArray goodsDetail = new JSONArray();
//        JSONObject goods1 = new JSONObject();
//        goods1.put("goods_id", "goodsNo1");
//        goods1.put("goods_name", "子商品1");
//        goods1.put("quantity", 1);
//        goods1.put("price", 0.01);
//        goodsDetail.add(goods1);
//        bizContent.put("goods_detail", goodsDetail);
//        //          // 扩展信息，按需传入
//        JSONObject extendParams = new JSONObject();
//        extendParams.put("sys_service_provider_id", "2088511833207846");
//        bizContent.put("extend_params", extendParams);
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        String tradeNo = response.getTradeNo();
        System.out.println(tradeNo);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
//            httpResponse.setContentType("text/html;charset=" + "UTF-8");
//            httpResponse.getWriter().write(response.getBody());          // 直接将完整的表单html输出到页面
//            httpResponse.getWriter().flush();
//            httpResponse.getWriter().close();
            System.out.println("成功");
            System.out.println(response.toString());
            System.out.println(response.getCode());
            System.out.println(response.getMsg());
            System.out.println(response.getTradeNo());
            System.out.println(response.getOutTradeNo());
            System.out.println(response.getMerchantOrderNo());
            return response.getBody();
        } else {
            System.out.println("失败");
        }
        return null;
    }
    @Autowired
    private ApiData alipayConfig;
    @PostMapping("/notify")       // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        //支付宝回调
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                          // System.out.println(name + " = " + request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayConfig.getPublicKey(), "UTF-8");           // 验证签名
                      // 支付宝验签
            if (checkSignature) {
                          // 验签通过
                //向支付宝存一笔交易流水
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));

                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            }
        }
        return "success";
    }
              //主动查询
    @GetMapping("show/{sss}")
    public void show(@PathVariable("sss") String ss) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", ss);
          //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(JSON.toJSONString(response));
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
    @GetMapping("getToken")
    public Result<String> getToken(){
        String token = request.getHeader("token");
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(token,s);
        return Result.success(s);
    }
    @Autowired
    private ShopFeign shopFeign;
    @GetMapping("buySku/{id}")
    public Result<String> buySku(@PathVariable("id") Long id){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String ss = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(ss, UserEntityVo.class);
        SkuEntity skuEntity = shopFeign.getInfo(id);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(notifyUrl);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(returnUrl);
/******必传参数******/
        JSONObject bizContent = new JSONObject();
        String s = IdUtil.getSnowflake(20, 1).nextIdStr();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", s);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", skuEntity.getSkuPrice());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", skuEntity.getSkuTitle());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
///******可选参数******/
        //
//        bizContent.put("time_expire", new Date());
//        //          // 商品明细信息，按需传入
//        JSONArray goodsDetail = new JSONArray();
//        JSONObject goods1 = new JSONObject();
//        goods1.put("goods_id", "goodsNo1");
//        goods1.put("goods_name", "子商品1");
//        goods1.put("quantity", 1);
//        goods1.put("price", 0.01);
//        goodsDetail.add(goods1);
//        bizContent.put("goods_detail", goodsDetail);
//        //          // 扩展信息，按需传入
//        JSONObject extendParams = new JSONObject();
//        extendParams.put("sys_service_provider_id", "2088511833207846");
//        bizContent.put("extend_params", extendParams);
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        String tradeNo = response.getTradeNo();
        System.out.println(response.getBody());
        if (response.isSuccess()) {
//            httpResponse.setContentType("text/html;charset=" + "UTF-8");
//            httpResponse.getWriter().write(response.getBody());          // 直接将完整的表单html输出到页面
//            httpResponse.getWriter().flush();
//            httpResponse.getWriter().close();
            System.out.println("成功");
            System.out.println(response.toString());
            System.out.println(response.getCode());
            System.out.println(response.getMsg());
            System.out.println(response.getTradeNo());
            System.out.println(response.getOutTradeNo());
            System.out.println(response.getMerchantOrderNo());
            return Result.success(response.getBody());
        } else {
            System.out.println("失败");
        }
        return Result.error();
    }
    @Autowired
    private AlipayUtil alipayUtil;
    @PostMapping("/refund")
    public String refund( String outTradeNo,String tradeNo,
                          String transIn, BigDecimal refundAmount){
        RefundEntity refundEntity = new RefundEntity();
        refundEntity.setOutTradeNo(outTradeNo);
        refundEntity.setTradeNo(tradeNo);
        refundEntity.setTransIn(transIn);
        refundEntity.setRefundAmount(refundAmount);
        String refund = alipayUtil.refund(refundEntity);
        System.out.println("退款通知:"+refund);
        return null;
    }
}
