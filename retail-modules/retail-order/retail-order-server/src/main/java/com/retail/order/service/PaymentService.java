package com.retail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.order.domain.PayMentEntity;

import java.util.List;


/**
 * 支付信息表
 *
 * @author fengge
 * @email fengge@atguigu.com
 * @date 2022-03-21 19:10:45
 */
public interface PaymentService extends IService<PayMentEntity> {

    Result<List<PayMentEntity>> getPaymentList();

    void createColonelOrderPay(PayMentEntity payMentEntity);
}

