package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 用户/优惠卷中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retai_user_coupon_center")
public class RetaiUserCouponCenterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 优惠卷id
	 */
	private Integer couponId;

}
