package com.retail.user.controller;

import java.util.List;


import cn.hutool.core.bean.BeanUtil;
import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.result.Result;
import com.retail.user.domain.IntegrationHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.user.service.IntegrationHistoryService;


/**
 * 购物积分记录表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@RestController
@RequestMapping("user/integrationhistory")
public class IntegrationHistoryController {

    @Autowired
    private IntegrationHistoryService integrationHistoryService;

    //  user/integrationhistory/integrationHistoryInsert
    @PostMapping("/integrationHistoryInsert")
    public Result integrationHistoryInsert(@RequestBody IntegrationHistoryEntityVo integrationHistoryEntityVo){
        IntegrationHistoryEntity integrationHistoryEntity = new IntegrationHistoryEntity();
        BeanUtil.copyProperties(integrationHistoryEntityVo,integrationHistoryEntity);
        return integrationHistoryService.insert(integrationHistoryEntity);
    }

}
