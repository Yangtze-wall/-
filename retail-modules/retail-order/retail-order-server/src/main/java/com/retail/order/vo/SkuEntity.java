package com.retail.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
@Data
public class SkuEntity{

	/**
	 * sku id
	 */
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
	 * 库存
	 */
	private Integer skuStock;
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
