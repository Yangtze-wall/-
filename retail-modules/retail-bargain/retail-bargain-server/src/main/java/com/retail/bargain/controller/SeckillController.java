package com.retail.bargain.controller;

import java.util.List;


import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.vo.SeckillEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.service.SeckillService;


/**
 * 秒杀商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@RestController
@RequestMapping("bargain/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 秒杀商品首页列表
     */
    @PostMapping
    public List<SeckillEntityVo> list(@RequestBody SeckillRequest seckillRequest){
        return seckillService.seckillList(seckillRequest);
    }

}
