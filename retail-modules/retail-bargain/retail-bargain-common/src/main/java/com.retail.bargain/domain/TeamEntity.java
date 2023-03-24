package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 拼团表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_team")
public class TeamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团表主键
	 */
	@TableId
	private Long id;
	/**
	 * 商品id
	 */
	private Long shopId;
	/**
	 * 商品标题
	 */
	private String shopHeadline;
	/**
	 * 商品拼团价格
	 */
	private BigDecimal shopTeamPrice;
	/**
	 * 所需人数
	 */
	private Integer needNumberPeople;
	/**
	 * 已参团人数
	 */
	private Integer teamNumberPeople;
	/**
	 * 状态
	 */
	private Long status;
	/**
	 * 团长id
	 */
	private Long colonelId;
	/**
	 * 发起拼团时间
	 */
	private Date teamStartTime;
	/**
	 * 拼团结束时间
	 */
	private Date teamEndTime;
	/**
	 * 拼团完成时间
	 */
	private Date teamAccomplishTime;
	/**
	 * 配送方式
	 */
	private String distribution;

}
