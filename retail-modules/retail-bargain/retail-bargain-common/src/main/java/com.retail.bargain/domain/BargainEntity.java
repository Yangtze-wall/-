package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 砍价表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_bargain")
public class BargainEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 砍价表主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 砍价活动名称
	 */
	private String bargainName;
	/**
	 * 砍价活动简介
	 */
	private String bargainIntroductory;
	/**
	 * 商品id
	 */
	private Long shopId;
	/**
	 * 已砍百分比
	 */
	private BigDecimal bargainAccomplish;
	/**
	 * 剩余百分比
	 */
	private BigDecimal bargainUnfinished;
	/**
	 * 需要人数
	 */
	private Integer needNumberPeople;
	/**
	 * 总过期时间
	 */
	private Date totalExpirationTime;
	/**
	 * 砍价状态
	 */
	private Long bargainStatus;
	/**
	 * 成功砍价人数
	 */
	private Integer bargainNumberPeople;
	/**
	 * 砍价人id
	 */
	private Long userId;
	/**
	 * 状态
	 */
	private Long status;

}
