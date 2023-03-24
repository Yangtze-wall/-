package com.retail.order.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 快递公司
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:07:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_expressage")
public class ExpressageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 快递公司主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 快递公司名称
	 */
	private String expressageName;
	/**
	 * 状态
	 */
	private Long status;

}
