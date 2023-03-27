package com.retail.order.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 订单表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:07:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 商品单号
	 */
	private String orderSn;
	/**
	 *  总价钱
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
	private Integer corporationId;
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
	private Integer couponId;
	/**
	 * 下单时间
	 */
	private Date createTime;
	/**
	 * 结账时间
	 */
	private Date realityTime;
	/**
	 * 订单类型
	 */
	private Integer type;
	/**
	 * 拼团商品id
	 */
	private Long seckillId;
	/**
	 * 地址id
	 */
	private Long addressId;

}
