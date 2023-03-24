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
 * 关注店铺表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_user_collect_shop")
public class UserCollectShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 店铺id
	 */
	private Long shopId;
	/**
	 * 店铺名
	 */
	private String shopName;
	/**
	 * 店铺logo
	 */
	private String shopLogo;
	/**
	 * 关注时间
	 */
	private Date createtime;

}
