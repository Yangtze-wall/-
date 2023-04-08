package com.retail.user.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import com.retail.user.domain.IntegrationHistoryEntity;
import com.retail.user.domain.UserEntity;
import com.retail.user.domain.vo.SignTimeSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.retail.user.service.IntegrationHistoryService;

import javax.servlet.http.HttpServletRequest;


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
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private IntegrationHistoryService integrationHistoryService;

    /**
     * 用户签到 新添一条数据 type=1（签到）送积分
     * @param integrationHistoryEntity
     * @return
     */
    @PostMapping("/insertSign")
    public Result insertSign(@RequestBody IntegrationHistoryEntity integrationHistoryEntity){

        return integrationHistoryService.insertSign(integrationHistoryEntity);
    }


    /**
     * 用户签到，可以补签
     * @param dateStr 查询的日期，默认当天 yyyy-MM-dd
     * @return 连续签到次数和总签到次数
     */
    @PostMapping("/doSignIn/{dateStr}")
    public Result<Map<String, Object>>doSignIn(@PathVariable("dateStr") String dateStr) {
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntity userEntity = JSON.parseObject(s, UserEntity.class);
        // userId 用户ID
        Map<String, Object> stringObjectMap = integrationHistoryService.doSign(userEntity.getId(), dateStr);
        return Result.success(stringObjectMap);
    }




}
