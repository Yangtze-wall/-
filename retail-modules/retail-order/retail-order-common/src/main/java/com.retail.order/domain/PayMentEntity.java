package com.retail.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.colonel.domain
 * @ClassName:      PayMentEntity
 * @Author:     2766395184
 * @Description:  支付表
 * @Date:    2023/4/3 17:25
 * @Version:    1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_payment")
public class PayMentEntity {


    private Long    id;//支付表主键

    private String orderSn; //订单号

    private String outTradeNo; //订单号

    private String    alipayTradeNo; //支付流水号

    private String    totalAmount; //支付价格

    private   String  subject; //交易内容

    private  String   paymentStatus; //订单状态

    private String createTime; //创建时间

    private  String   confirmTime; //确认时间

    private   String  callbackContent; //回调内容

    private  String   callbackTime;//回调时间



}
