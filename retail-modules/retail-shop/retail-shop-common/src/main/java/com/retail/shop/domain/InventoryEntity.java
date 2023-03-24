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
 * 库存表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:56:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_inventory")
public class InventoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 库存表主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 商品id
	 */
	private Integer spuId;
	/**
	 * 库存数量
	 */
	private Integer inventoryCount;
	/**
	 * 入库时间
	 */
	private Date inventoryCreateTime;
	/**
	 * 锁库存
	 */
	private Integer inventoryLock;
	/**
	 * 出售数量
	 */
	private Integer inventorySellCount;

}
