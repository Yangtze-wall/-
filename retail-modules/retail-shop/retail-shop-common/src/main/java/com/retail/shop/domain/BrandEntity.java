package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品品牌表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
@Data
@TableName("retail_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品名称
	 */
	private String brandName;
	/**
	 * 品牌logo
	 */
	private String brandLogo;
	/**
	 * 品牌状态
	 */
	private Integer brandStatus;
	/**
	 * 排序
	 */
	private Integer sort;

}
