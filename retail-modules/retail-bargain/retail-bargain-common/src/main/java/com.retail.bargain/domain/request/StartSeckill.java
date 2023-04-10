package com.retail.bargain.domain.request;

import lombok.Data;

/**
 * 点击开始秒杀
 *
 * @author Lenovo
 * @Package_name com.retail.bargain.domain.request
 * @Description TODO
 * @createTime 2023/3/28 11:42
 */
@Data
public class StartSeckill {
    /**
     * 秒杀商品id
     */
    private Long seckillId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 地址主键
     */
    private Integer addressId;
    /**
     * 备注
     */
    private String remark;



    /**
     * 订单id
     */
    private Long orderId;
}
