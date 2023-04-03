package com.retail.user.controller;

import java.util.List;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.retail.common.result.Result;
import com.retail.user.domain.request.UserIntegralAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.user.service.IntegrationHistoryService;


/**
 * 购物积分记录表
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@RestController
@RequestMapping("user/integrationhistory")
public class IntegrationHistoryController {

    @Autowired
    private IntegrationHistoryService integrationHistoryService;

    /**
     * 积分增加
     */
    @PostMapping("/add")
    public Result add(@RequestBody UserIntegralAdd userIntegralAdd) {
        Result result =  integrationHistoryService.add(userIntegralAdd);
        return result;
    }
}
