package com.retail.shop.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 店铺表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_shop")
public class ShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品id
	 */
	private Integer spuId;
	/**
	 * 创建时间
	 */
	private Date shopCreatetime;
	/**
	 * 店长姓名
	 */
	private String shopManager;
	/**
	 * 店长手机号
	 */
	private String shopTel;
	/**
	 * 店铺logo
	 */
	private String shopLogo;
	/**
	 * 店铺背景
	 */
	private String shopImage;
	/**
	 * 店铺地址
	 */
	private String shopAddress;
	/**
	 * 配送费
	 */
	private BigDecimal shopDelivery;
	/**
	 * 0 配送 1 不配送
	 */
	private Integer shopDeliverystatus;
	/**
	 * 体现扣点
	 */
	private BigDecimal shopDeduction;
	/**
	 * 店铺状态
	 */
	private Integer shopStatus;

}
