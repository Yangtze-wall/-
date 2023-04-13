package com.retail.colonel.controller;

import java.util.List;
import java.util.Set;


import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.retail.colonel.service.ColonelService;


/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("colonel/colonel")
public class ColonelController {

    @Autowired
    private ColonelService colonelService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


}
