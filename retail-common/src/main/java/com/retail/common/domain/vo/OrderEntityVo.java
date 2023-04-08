package com.retail.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.common.domain.vo
 * @Classname: OrderEntityVo
 * @CreateTime: 2023-04-06  21:34
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 快递公司id
     */
    private Long corporationId;
    /**
     * 优惠卷id
     */
    private Long couponId;
    /**
     * 备注
     */
    private String remark;
    /**
     *  秒杀id
     */
    private Long seckillId;
    /**
     * 收货地址id
     */
    private Long addressId;
    /**
     *  砍价表id
     */
    private Long bargainId;

    /**
     * 拼团id
     */
    private Long teamId;

    /**
     * skuId
     */
    private Long skuId;







}
