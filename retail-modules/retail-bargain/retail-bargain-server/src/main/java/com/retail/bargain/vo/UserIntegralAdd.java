package com.retail.bargain.vo;

import lombok.Data;

/**
 * @author Lenovo
 * @Package_name com.retail.user.domain.request
 * @Description TODO
 * @createTime 2023/3/30 14:57
 */
@Data
public class UserIntegralAdd {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 积分
     */
    private Integer integral;

    /**
     * 获取积分类型 （1签到，2购买，3兑换优惠券）
     */
    private Integer sourceType;
}
