package com.retail.colonel.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 团长路线表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_route")
public class RouteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长路线
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 团长id
	 */
	private Long colonelId;
	/**
	 * 仓库名称
	 */
	private String warehouseName;
	/**
	 * 联系人姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String addres;
	/**
	 * 状态
	 */
	private Integer status;

}
