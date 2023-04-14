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
 * 账户记录流水表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecordEntityVo implements Serializable {
	/**
	 *
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 订单号(账户流水号)
	 */
	private String orderSn;
	/**
	 * 金额
	 */
	private BigDecimal price;
	/**
	 * 类型(1充值，2提现，3消费，4返佣金, 5.退款)
	 */
	private Integer rechargeType;
	/**
	 * 确认时间
	 */
	private Date payTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 描述
	 */
	private String remark;

}