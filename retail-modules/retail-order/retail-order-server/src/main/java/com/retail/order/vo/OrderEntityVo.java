package com.retail.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * @Package_name com.retail.order.vo
 * @Description TODO
 * @createTime 2023/4/3 10:38
 */
@Data
public class OrderEntityVo {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品图片
     */
    private String imgUrl;
    /**
     * 商品简介
     */
    private String desc;
    /**
     * 商品名称
     */
    private String spuaNme;
    /**
     * 订单状态  1待支付  2支付成功  3支付失败
     */
    private Integer status;
    /**
     * 订单总价
     */
    private BigDecimal totalPrice;
    /**
     * 优惠金额
     */
    private BigDecimal couponPrice;
    /**
     * 支付金额
     */
    private BigDecimal payPrice;

}
