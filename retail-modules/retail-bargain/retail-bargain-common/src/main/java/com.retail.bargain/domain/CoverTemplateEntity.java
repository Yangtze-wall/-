package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 配送模板表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_cover_template")
public class CoverTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *  运费配置模板
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 计费方式
	 */
	private BigDecimal type;
	/**
	 * 是否包邮
	 */
	private Integer status;
	/**
	 * order by
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 更新人
	 */
	private Long updateBy;

}
