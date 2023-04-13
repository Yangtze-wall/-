package com.retail.order.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.example.demo.common
 * @Description TODO
 * @createTime 2023/3/30 11:30
 */
@Data
public class RefundEntity {
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 收入方账户
     */
    private String transIn;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
}
