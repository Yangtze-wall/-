package com.retail.common.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class OrderEntityVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 快递公司id
	 */
	private Long corporationId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 优惠券id
	 */
	private Long couponId;
	/**
	 * 收货地址id
	 */
	private Long addressId;
	/**
	 * 积分
	 */
	private Integer integration;

	private Long spuId;
	/**
	 *  砍价表id
	 */
	private Long bargainId;

}
