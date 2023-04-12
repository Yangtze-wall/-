package com.retail.common.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.common.domain.vo
 * @ClassName:      CommissionVo
 * @Author:     2766395184
 * @Description:  佣金流水表
 * @Date:    2023/3/24 20:43
 * @Version:    1.0
 */
@Data
public class CommissionVo {
    /**
     *
     */

    private Long id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 订单号(账户流水号)
     */
    private String orderSn;
    /**
     * 金额
     */
    private BigDecimal price;
    /**
     * 类型(1充值，2提现，3消费，4返佣金, 5.退款)
     */
    private String rechargeType;
    /**
     * 确认时间
     */
    private Date payTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 描述
     */
    private String describe;


    private Integer pageNum=1;
    private Integer pageSize=10;
}
