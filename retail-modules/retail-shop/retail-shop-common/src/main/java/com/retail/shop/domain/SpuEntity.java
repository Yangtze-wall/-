package com.retail.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */
@Data
@TableName("retail_spu")
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
	private Long shopsId;
	/**
	 * 商品上架时间
	 */
	private Date spuCreatetime;
	/**
	 * 商品修改时间
	 */
	private Date spuUpdatetime;
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

}
