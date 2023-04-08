package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
@Data
@TableName("retail_sku")
public class SkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品图片
	 */
	private String skuImage;
	/**
	 * 商品标题
	 */
	private String skuTitle;
	/**
	 * 副标题
	 */
	private String skuSubhead;
	/**
	 * 销量
	 */
	private Integer skuSell;
	/**
	 * 价格
	 */
	private BigDecimal skuPrice;
	/**
	 * spuid
	 */
	private Long spuId;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 分类id
	 */
	private Long classifiedId;

}
