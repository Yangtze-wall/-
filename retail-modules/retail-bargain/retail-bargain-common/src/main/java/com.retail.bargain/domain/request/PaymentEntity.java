package com.retail.bargain.domain.request;

import lombok.Data;

/**
 * @author Lenovo
 * @Package_name com.retail.order.domain
 * @Description TODO
 * @createTime 2023/3/29 19:27
 */
@Data
public class PaymentEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单ID
     */
    private Long orderSn;

}
