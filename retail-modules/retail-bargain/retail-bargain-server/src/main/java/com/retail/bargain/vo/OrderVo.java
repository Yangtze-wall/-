package com.retail.bargain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.vo
 * @Description TODO
 * @createTime 2023/3/28 18:21
 */
@Data
public class OrderVo {

    /**
     * 订单id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 总价钱
     */
    private BigDecimal totalAmount;

    /**
     * 积分抵扣金额
     */
    private BigDecimal integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 运费金额
     */
    private BigDecimal freightAmount;

    /**
     * 应付价钱
     */
    private BigDecimal payAmount;

    /**
     * 状态(1.待支付 2.支付成功 3.支付失败)
     */
    private Integer status;

    /**
     * 快递公司id
     */
    private Long corporationId;

    /**
     * 收件人姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 生成下单时间
     */
    private Date createTime;

    /**
     * 结账时间
     */
    private Date realityTime;

    /**
     * 类型(1.正常,2.拼团,3秒杀 4.砍价)
     */
    private Integer type;

    /**
     * 秒杀id
     */
    private Long seckillId;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 砍价id
     */
    private Long bargainId;

    /**
     * 拼团id
     */
    private Long teamId;

    /**
     * spuId
     */
    private Long spuId;
    /**
     * skuId
     */
    private Long skuId;
}
