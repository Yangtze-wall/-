package com.retail.bargain.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.PayRequest;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.domain.request.StartSeckill;
import com.retail.bargain.feign.InventoryFeign;
import com.retail.bargain.feign.OrderFeign;
import com.retail.bargain.feign.UserFeign;
import com.retail.bargain.service.SeckillService;
import com.retail.bargain.util.AlipayUtil;
import com.retail.bargain.vo.OrderVo;
import com.retail.bargain.vo.Payment;
import com.retail.bargain.vo.UserIntegralAdd;
import com.retail.bargain.vo.UserRecordEntity;
import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.common.utils.DESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: retail-cloud
 * @Package: com.retail.colonel.controller
 * @ClassName: SeckillController
 * @Author: 2766395184
 * @Description: 秒杀控制层
 * @Date: 2023/3/25 9:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("colonel/seckill")
@RequiredArgsConstructor
@Log4j2
public class SeckillController {


    @Resource
    private SeckillService seckillService;
    @Value("${des.decryptKey}")
    private String decryptKey;
    @Value("${aliPay.publicKey}")
    private String publicKey;
    @Resource
    private AlipayUtil alipayUtil;
    @Resource
    private UserFeign userFeign;
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private InventoryFeign inventoryFeign;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     * 秒杀首页 名称,时间区间查,分页
     *
     * @param cipherText
     * @return
     */
    @PostMapping("list")
    public Result<PageResult<SeckillEntity>> list(@RequestBody String cipherText) {
        String decrypt = DESUtil.aesDecryptForFront(cipherText, decryptKey);
        SeckillRequest request = JSONObject.parseObject(decrypt, SeckillRequest.class);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SeckillEntity> list = seckillService.seckillList(request);
        PageInfo<SeckillEntity> pageInfo = new PageInfo<>(list);
        Result<PageResult<SeckillEntity>> result = PageResult.toResult(pageInfo.getTotal(), list);
        return result;
    }


    /**
     * 秒杀商品添加
     */
    @PostMapping("add")
    public Result<SeckillEntity> add(@RequestBody SeckillEntity entity) {
        Result result = seckillService.seckillAdd(entity);
        return result;
    }

//    /**
//     * 秒杀商品明细
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("itemDetail/{id}")
//    public Result<SeckillSpuVo> itemDetail(@PathVariable("id") Long id) {
//
//        if (id == null) {
//            Result.error(502, "请选择正确的秒杀商品");
//        }
//
//        Result<SeckillSpuVo> seckillEntity = seckillService.itemDetail(id);
//
//        return seckillEntity;
//    }

    /**
     * 根据id查询秒杀商品详情
     */
    @PostMapping("itemDetail/{id}")
    public Result<SeckillSpuVo> itemDetail(@PathVariable("id") Long spuId) {
        SeckillSpuVo spuVo = seckillService.itemDetail(spuId);
        return Result.success(spuVo);
    }

    /**
     * 点击开始秒杀
     */
    @PostMapping("start")
    public Result<SeckillEntity> start(@RequestBody StartSeckill seckill) {
        Result result = seckillService.start(seckill);
        return result;
    }


    /**
     * 支付
     */
    @PostMapping("pay")
    public Result pay(@RequestBody PayRequest payRequest) {
        //   viesrx6652@sandbox.com
        Result result = seckillService.pay(payRequest);
        return result;
    }

    /**
     * 异步回调通知
     */
    @PostMapping("notify")
    public String payNotify(HttpServletRequest request) throws Exception {
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

//            System.out.println(params.get("out_trade_no"));
//            System.out.println(params.get("gmt_payment"));
//            System.out.println(params.get("trade_no"));

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            // 验证签名
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, publicKey, "UTF-8");
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("交易名称: " + params.get("subject"));
                log.info("交易状态: " + params.get("trade_status"));
                log.info("支付宝交易凭证号: " + params.get("trade_no"));
                log.info("商户订单号: " + params.get("out_trade_no"));
                log.info("交易金额: " + params.get("total_amount"));
                log.info("买家在支付宝唯一id: " + params.get("buyer_id"));
                log.info("买家付款时间: " + params.get("gmt_payment"));
                log.info("买家付款金额: " + params.get("buyer_pay_amount"));
                //积分增加 1根据订单号查询订单信息  2计算积分
                //查询订单信息
                String outTradeNo = params.get("out_trade_no");
                Result<OrderVo> orderVoResult = orderFeign.orderFindByOrderSn(outTradeNo);
                OrderVo orderInfo = orderVoResult.getData();
                //计算积分
                String totalAmount = params.get("total_amount");
                Double aDouble = Double.valueOf(totalAmount);
                BigDecimal money = BigDecimal.valueOf(aDouble);
                //参数添加  用户id  积分 积分类型
                UserIntegralAdd userIntegralAdd = new UserIntegralAdd();
                userIntegralAdd.setUserId(orderInfo.getUserId());
                userIntegralAdd.setIntegral(money.intValue());
                userIntegralAdd.setSourceType(2);
                userFeign.add(userIntegralAdd);

                //订单表状态修改  添加结账时间
                orderFeign.updateOrderStatus(orderInfo.getOrderSn());

                //用户支付流水表添加
                UserRecordEntity userRecordEntity = new UserRecordEntity();
                userRecordEntity.setCreateTime(new Date());
                userRecordEntity.setOrderSn(orderInfo.getOrderSn());
                userRecordEntity.setPrice(orderInfo.getPayAmount());
                userRecordEntity.setRemark("支付流水");
                userRecordEntity.setUserId(orderInfo.getUserId());
                userRecordEntity.setRechargeType(3);
                userFeign.recordAdd(userRecordEntity);

                //支付表添加
                Payment payment = new Payment();
                payment.setOrderSn(orderInfo.getOrderSn());
                payment.setAlipayTradeNo(params.get("trade_no"));
                payment.setTotalAmount(params.get("total_amount"));
                payment.setSubject("秒杀支付");
                payment.setPaymentStatus(params.get("trade_status"));
                payment.setCreateTime(new Date());
                payment.setCallbackContent("6666");
                payment.setOutTradeNo(params.get("out_trade_no"));
                seckillService.paymentAdd(payment);


                //库存表修改 (spu 购买数量)   库存=库存-购买数量   预减库存=预减库存-购买数量
                //根据spuId查询 库存
                InventoryVo inventory = inventoryFeign.selectInventory(orderInfo.getSpuId());
                InventoryVo inventoryVo = new InventoryVo();
                BeanUtil.copyProperties(inventory, inventoryVo);
                //库存计算
                inventoryVo.setInventoryCount(inventory.getInventoryCount() - 1);
                //预减库存计算
                inventoryVo.setInventoryLock(inventory.getInventoryLock()-1);
                //售卖数量计算
                inventoryVo.setInventorySellCount(inventory.getInventorySellCount()+1);
                inventoryFeign.updateInventory(inventoryVo);
            }
        }
        return "success";
    }


}
