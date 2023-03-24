package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 首页轮播图表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_first_cycle_image")
public class FirstCycleImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 轮播图片地址
	 */
	private String picture;
	/**
	 * 描述
	 */
	private String describe;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 跳转地址
	 */
	private String linkUrl;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 商品spuId
	 */
	private Long spuId;

}
