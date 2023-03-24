package com.retail.colonel.domain;

import com.baomidou.mybatisplus.annotation.IdType;import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 团长申请表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_colonel_apply")
public class ColonelApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长申请id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 头像
	 */
	private String picture;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 身份证号
	 */
	private Integer idcord;
	/**
	 * 所属城市
	 */
	private String city;
	/**
	 * 自提点详细地址信息
	 */
	private String addressInfo;
	/**
	 * 状态(1.待审核 2. 成功 3.失败)
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 审核时间
	 */
	private Date verifyTime;
	/**
	 * 管理员
	 */
	private Integer admin;
	/**
	 * 申请人
	 */
	private Integer userId;
	/**
	 * 身份证 正面图片
	 */
	private String idcordFront;
	/**
	 * 身份证 反面图片
	 */
	private String idcordReverse;
	/**
	 * 活体检测
	 */
	private String bioassay;

}
