package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.retail.common.domain.vo.ProductVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
@Data
@TableName("retail_spu")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品表
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品名称
	 */
	private String spuName;
	/**
	 * 商品介绍
	 */
	private String spuLetter;
	/**
	 * 商家id
	 */
	private Long shopId;
	/**
	 * 商品上架时间
	 */
	private Date spuCreateTime;
	/**
	 * 商品修改时间
	 */
	private Date spuUpdateTime;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 1 上架 2 下架
	 */
	private Integer spuStatus;
	/**
	 * 1 同步 2 未同步
	 */
	private Integer esStatus;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 分类id
	 */
	private Long classifiedId;


	public static SpuEntity insertSpuEntity(ProductVo productVo) {
		return SpuEntity.builder()
				.sort(productVo.getSort())
				.brandId(productVo.getBrandId())
				.classifiedId(Long.valueOf(productVo.getClassifiedIds()[2]))
				.spuLetter(productVo.getSpuLetter())
				.spuName(productVo.getSpuName())
				.spuCreateTime(new Date())
				.spuStatus(1)
				.esStatus(1)
				.shopId(1L)
				.spuUpdateTime(new Date()).build();
	}
}
