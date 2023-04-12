package com.retail.bargain.domain.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.domain.response
 * @Description TODO
 * @createTime 2023/4/6 22:07
 */
@Data
public class PayResponse {
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 买家支付宝账号
     */
    private String buyerLogonId;
    /**
     * 交易状态 未付款:TRADE_CLOSED  支付成功:TRADE_SUCCESS  交易结束:TRADE_FINISHED
     */
    private String tradeStatus;
    /**
     * 交易金额
     */
    private BigDecimal totalAmount;
}
