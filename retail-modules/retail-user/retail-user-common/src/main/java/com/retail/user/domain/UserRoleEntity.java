package com.retail.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 权限表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_user_role")
public class UserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *  权限中间表
	 */
	@TableId(type = IdType.AUTO)(type = IdType.AUTO)
	private Long id;
	/**
	 * 权限id

	 */
	private Long roleId;
	/**
	 * 用户id
	 */
	private Long userId;

}
