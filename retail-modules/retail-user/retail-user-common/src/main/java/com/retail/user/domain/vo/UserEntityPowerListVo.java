package com.retail.user.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.retail.user.domain.PowerEntry;
import com.retail.user.domain.PowerUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class UserEntityPowerListVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickName;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 备注
	 */
	private String remark;

	private List<PowerEntry> powerEntryList;

	private String ids;


}
