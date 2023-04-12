package com.retail.bargain.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 支付表
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
@Data
public class Payment {


    /**
     * 支付表主键
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 支付流水号
     */
    private String alipayTradeNo;

    /**
     * 支付价格
     */
    private String totalAmount;

    /**
     * 交易内容
     */
    private String subject;

    /**
     * 订单状态
     */
    private String paymentStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 回调内容
     */
    private String callbackContent;

    /**
     * 回调时间
     */
    private Date callbackTime;

    /**
     * 商户订单号
     */
    private String outTradeNo;


}
