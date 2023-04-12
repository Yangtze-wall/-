package com.retail.shop.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 我的优惠券
 * @author Lenovo
 * @Package_name com.retail.shop.vo
 * @Description TODO
 * @createTime 2023/4/11 18:36
 */
@Data
public class CouponVo {
    /**
     * 优惠券表ID
     */
    private Long id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券面值
     */
    private BigDecimal money;
    /**
     * 0. 可以使用 1.被使用
     */
    private Integer isDel;
}
