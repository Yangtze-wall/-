package com.retail.bargain.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 砍价记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("retail_bargain_record")
public class BargainRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 砍价记录表主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 砍价表id
	 */
	private Long bargainId;
	/**
	 * 好友id
	 */
	private Long userId;
	/**
	 * 砍价百分比
	 */
	private Double bargainOrderAccomplish;
	/**
	 * 砍价价格
	 */
	private BigDecimal bargainOrderPrice;
	/**
	 * 砍价时间
	 */
	private Date bargainOrderTime;
	/**
	 * 状态
	 */
	private Long status;

}
