package com.retail.order.controller;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.*;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.order.config.AlipayUtile;
import com.retail.order.constants.ConstantsToken;
import com.retail.order.domain.AlipayBean;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.feign.ShopFeignService;
import com.retail.order.feign.UserFeignService;

import com.retail.order.mapper.PaymentMapper;
import com.retail.order.service.CartService;
import com.retail.order.service.OrderService;
import com.retail.order.utils.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private UserFeignService userFeignService;



    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ShopFeignService shopFeignService;

    @Autowired
    private CartService cartService;


    @Autowired
    private AlipayUtile alipayUtile;

    @Autowired
    private AlipayConfig alipayConfig;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public UserEntityVo userInfo(){
        String userKey = JwtUtils.getUserKey(ConstantsToken.token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY+userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }


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
        alipayBean.setOut_trade_no(orderEntity.getOrderSn());
        alipayBean.setTotal_amount(total_amount);
        alipayBean.setSubject(subject);
        alipayBean.setBody("支付付款");
        ConstantsToken.token = request.getHeader(TokenConstants.TOKEN);
//        String pay = alipayUtile.pay(alipayBean);

        return alipayUtile.pay(alipayBean);

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
        log.info("------------------------"+params.toString());
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

            //商户订单号
            String outTradeNo = params.get("out_trade_no");
            //买家付款时间
            String gmtPayment = params.get("gmt_payment");
            //买家付款金额
            String buyerPayAmount = params.get("buyer_pay_amount");
            //支付宝交易凭证号
            String tradeNo = params.get("trade_no");
            //交易名称
            String subject = params.get("subject");
            //交易状态
            String tradeStatus = params.get("trade_status");

            //添加支付表
            PayMentEntity payMentEntity = new PayMentEntity();
            payMentEntity.setOrderSn(outTradeNo);
            //支付宝交易凭证号
            payMentEntity.setAlipayTradeNo(tradeNo);
            payMentEntity.setTotalAmount(buyerPayAmount);
            payMentEntity.setSubject(subject);
            payMentEntity.setCreateTime(gmtPayment);
            payMentEntity.setOutTradeNo(outTradeNo);
            payMentEntity.setPaymentStatus(tradeStatus);
            paymentMapper.insert(payMentEntity);
            //账户记录流水表
            UserRecordEntityVo userRecordEntityVo = new UserRecordEntityVo();
            userRecordEntityVo.setCreateTime(new Date());
            //支付价格
            userRecordEntityVo.setPrice(new BigDecimal(buyerPayAmount));
            //类型(1充值，2提现，3消费，4返佣金, 5.退款)
            userRecordEntityVo.setRechargeType(1);
            userRecordEntityVo.setUserId(userInfo().getId());
            userRecordEntityVo.setPayTime(new Date());
            userRecordEntityVo.setRemark("支付成功");
            userRecordEntityVo.setOrderSn(outTradeNo);
            userFeignService.insertRecord(userRecordEntityVo);
            //积分记录表添加记录（添加积分）
            IntegrationHistoryEntityVo integrationHistoryEntityVo = new IntegrationHistoryEntityVo();
            integrationHistoryEntityVo.setCount(100);
            integrationHistoryEntityVo.setUserId(userInfo().getId());
            integrationHistoryEntityVo.setCreateTime(new Date());
            integrationHistoryEntityVo.setRemark("用户支付订单成功，加积分");
            //来源类型（1签到，2购买，3兑换优惠券）
            integrationHistoryEntityVo.setSourceType(2);
            userFeignService.insertIntegrationByOrder(integrationHistoryEntityVo);
            //获取用户积分
            Result<UserEntityVo> userEntityVoResult = userFeignService.findUserById(userInfo().getId());
            UserEntityVo userEntityVo = userEntityVoResult.getData();
            if (userEntityVo==null){
                throw new BizException("获取用户信息失败");
            }
            Integer integration = userEntityVo.getIntegration();
            //用户表修改积分数，添加积分
            userEntityVo.setIntegration(integration+100);
            userFeignService.updateUser(userEntityVo);
            //订单表修改支付状态
            //根据商户订单号 获取订单信息
            Result<OrderEntity> orderEntityResult = orderService.findByOrderSn(outTradeNo);
            OrderEntity orderEntity = orderEntityResult.getData();
            if (orderEntity==null){
                throw new BizException("获取用户订单信息失败");
            }
            //状态(1.待支付 2.支付成功 3.过期 4 正在支付 稍等
            orderEntity.setStatus(2);
            try {
                orderEntity.setRealityTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(gmtPayment));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            orderService.update(orderEntity,new UpdateWrapper<OrderEntity>().lambda().eq(OrderEntity::getId,orderEntity.getId()));

            //修改库存（出售数量增加，锁库存减 库存数量减）
            //获取商品库存信息
            Result<InventoryEntityVo> inventoryEntityVoResult = shopFeignService.findInventoryBySpuId(orderEntity.getSpuId());
            InventoryEntityVo inventoryEntityVo = inventoryEntityVoResult.getData();
            if (inventoryEntityVo==null){
                throw new BizException("获取商品库存信息失败");
            }
            //获取购物车信息
            Result<CartEntityVo> cartEntityVoResult = cartService.findCartByCartId(orderEntity.getCartId());
            CartEntityVo cartEntityVo = cartEntityVoResult.getData();
            if (cartEntityVo==null){
                throw new BizException("获取购物车信息失败");
            }
            Integer buyNum = cartEntityVo.getBuyNum();
            // 修改 锁库存数量(减 购买数量)
            inventoryEntityVo.setInventoryLock(inventoryEntityVo.getInventoryLock()-buyNum);
            //出售数量 加
            inventoryEntityVo.setInventorySellCount(inventoryEntityVo.getInventorySellCount()+buyNum);
            //库存数量 减
            inventoryEntityVo.setInventoryCount(inventoryEntityVo.getInventoryCount()-buyNum);
            shopFeignService.updateInventory(inventoryEntityVo);
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
