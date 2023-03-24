package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 商品规格表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_specification")
public class SpecificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 规格名
	 */
	private String specificationName;
	/**
	 * 规格值
	 */
	private String specificationValue;
	/**
	 * skuId
	 */
	private Integer skuId;

}
