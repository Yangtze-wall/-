package com.retail.order.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 订单详情表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:07:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("reatil_order_info")
public class ReatilOrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单详情表
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 订单id
	 */
	private Integer orderSn;
	/**
	 * 商品spuId
	 */
	private Integer spuId;
	/**
	 * 商品总数量
	 */
	private Integer count;
	/**
	 * 商品总价
	 */
	private BigDecimal totalAmount;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
