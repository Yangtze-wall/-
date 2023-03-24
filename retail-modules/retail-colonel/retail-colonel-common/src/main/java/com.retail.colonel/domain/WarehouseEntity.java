package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 仓库表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_warehouse")
public class WarehouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 仓库表主键
	 */
	@TableId
	private Long id;
	/**
	 * 仓库名称
	 */
	private String warehouseName;
	/**
	 * 仓库地址
	 */
	private String warehouseAddress;
	/**
	 * 坐标
	 */
	private String warehouseCoordinate;
	/**
	 * 状态
	 */
	private Long status;

}
