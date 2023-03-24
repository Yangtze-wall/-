package com.retail.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 用户表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 头像
	 */
	private String headerImages;
	/**
	 * 用户性别（0男 1女 2未知)
	 */
	private Integer gender;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 购物积分
	 */
	private Integer integration;
	/**
	 * 帐号状态（0正常 1停用）
	 */
	private Integer status;
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 用户余额
	 */
	private Integer balance;
	/**
	 * 最后登录时间
	 */
	private Date loginDate;
	/**
	 * 备注
	 */
	private String remark;

}
