package com.retail.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.common.result.Result;
import com.retail.order.domain.OrderEntity;
import com.retail.order.domain.PayMentEntity;
import com.retail.order.mapper.OrderMapper;
import com.retail.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;


/**
 * 支付信息表
 *
 * @author fengge
 * @email fengge@atguigu.com
 * @date 2022-03-21 19:10:45
 */
@Slf4j
@RestController
@RequestMapping("order/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderMapper orderMapper;



    //  order/payment/getPaymentList
    @PostMapping("/getPaymentList")
    public Result<List<PayMentEntity>> getPaymentList(){
        return paymentService.getPaymentList();
    }





}
