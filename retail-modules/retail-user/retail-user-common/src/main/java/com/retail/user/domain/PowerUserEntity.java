package com.retail.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限  用户 中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 19:31:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_power_user")
public class PowerUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */

	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 *
	 */
	private Long userId;
	/**
	 *
	 */
	private Long powerId;

}
