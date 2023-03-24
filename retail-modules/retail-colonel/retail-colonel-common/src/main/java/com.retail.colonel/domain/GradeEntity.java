package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 团长等级
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_grade")
public class GradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长等级id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 等级名称
	 */
	private String name;
	/**
	 * 等级经验
	 */
	private Long experience;
	/**
	 * 比例为%
	 */
	private Double scale;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
