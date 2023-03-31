package com.retail.order.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.order.domain.ExpressageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.retail.order.service.ExpressageService;

import javax.servlet.http.HttpServletRequest;


/**
 * 快递公司
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:07:25
 */
@RestController
@RequestMapping("order/expressage")
public class ExpressageController {

    @Autowired
    private ExpressageService expressageService;

    @Autowired
    private HttpServletRequest request;

//    /order/expressage/getExpressageList
    @PostMapping("/getExpressageList")
    public Result<List<ExpressageEntity>> getExpressageList(){
        return expressageService.getExpressageList();
    }
}
