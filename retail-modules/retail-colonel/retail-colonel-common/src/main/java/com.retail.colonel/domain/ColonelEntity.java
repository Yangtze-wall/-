package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_colonel")
public class ColonelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 等级id
	 */
	private Integer gradeId;
	/**
	 * 身份证 正面图片
	 */
	private String idcordFront;
	/**
	 * 身份证 反面图片
	 */
	private String idcordReverse;

}
