package com.retail.bargain.controller;

import com.retail.bargain.domain.SeckillConfig;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.retail.bargain.service.SeckillConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 秒杀配置表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-25
 */
@Controller
@RequestMapping("/seckillConfig")
public class SeckillConfigController {


    @Autowired
    private SeckillConfigService seckillConfigService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SeckillConfig>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SeckillConfig> aPage = seckillConfigService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SeckillConfig> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(seckillConfigService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SeckillConfig params) {
        seckillConfigService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        seckillConfigService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SeckillConfig params) {
        seckillConfigService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
