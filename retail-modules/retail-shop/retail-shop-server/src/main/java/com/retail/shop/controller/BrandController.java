package com.retail.shop.controller;


import com.retail.shop.service.BrandService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 商品品牌表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;



}
