package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 拼团记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_team_record")
public class TeamRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团记录表主键
	 */
	@TableId
	private Long id;
	/**
	 * 拼团表id
	 */
	private Long teamId;
	/**
	 * 拼团人id
	 */
	private Long userId;
	/**
	 * 拼团时间
	 */
	private String teamOrderTime;
	/**
	 * 状态
	 */
	private Integer status;

}
