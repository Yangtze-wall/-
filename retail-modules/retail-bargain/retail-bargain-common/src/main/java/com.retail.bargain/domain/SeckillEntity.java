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
 * 秒杀商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_seckill")
public class SeckillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀表id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品id
	 */
	private Long shopId;
	/**
	 * 轮播图
	 */
	private String slideshow;
	/**
	 * 秒杀商品标题
	 */
	private String seckillHeadline;
	/**
	 * 秒杀价
	 */
	private BigDecimal seckillPrice;
	/**
	 * 秒杀开始时间
	 */
	private Date seckillStartTime;
	/**
	 * 秒杀结束时间
	 */
	private Date seckillEndTime;
	/**
	 * 添加时间
	 */
	private Date seckillCreateTime;
	/**
	 * 状态
	 */
	private Long status;
	/**
	 * 秒杀库存
	 */
	private Integer seckillCount;
	/**
	 * 剩余库存
	 */
	private Integer seckillResidueCount;
	/**
	 * 每人限购次数
	 */
	private Integer seckillLimit;
	/**
	 * 是否包邮
	 */
	private Integer postFree;

}
