package com.retail.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.order.service.PaymentService;


/**
 * 支付表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:56
 */
@RestController
@RequestMapping("order/retailpayment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


}
