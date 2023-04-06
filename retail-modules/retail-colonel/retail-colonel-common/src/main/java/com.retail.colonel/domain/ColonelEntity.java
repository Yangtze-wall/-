package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_colonel")
public class ColonelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 等级id
	 */
	private Long gradeId;
	/**
	 * 身份证 正面图片
	 */
	private String idcordFront;
	/**
	 * 身份证 反面图片
	 */
	private String idcordReverse;
	/**
	 当月佣金
	 */
	private BigDecimal monthCommission;
	/**
	 *  总共佣金
	 */
	private BigDecimal totalCommission;
	/**
	 *  已提现佣金
	 */
	private BigDecimal withdraw;
	/**
	 *  剩余佣金
	 */
	private BigDecimal surplusCommission;
	/**
	 * 团长详细地址(冗余字段)
	 */
	private String address;
}
