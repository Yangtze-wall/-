package com.retail.bargain.domain.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.domain.request
 * @Description TODO
 * @createTime 2023/4/6 20:41
 */
@Data
public class PayRequest {
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 支付金额
     */
    private BigDecimal totalAmount;
    /**
     * 订单标题
     */
    private String subject;
}
