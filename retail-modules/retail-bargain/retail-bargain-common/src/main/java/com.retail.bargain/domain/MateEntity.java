package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 配送表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_mate")
public class MateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配送编号
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 单号
	 */
	private String orderSn;
	/**
	 * 快递员姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 地址
	 */
	private String address;

}
