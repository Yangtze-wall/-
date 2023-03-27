package com.retail.colonel.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.retail.colonel.domain.Trend;
import com.retail.colonel.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 团长动态表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Controller
@RequestMapping("/trend")
public class TrendAction {


    @Autowired
    private TrendService trendService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Trend>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Trend> aPage = trendService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Trend> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(trendService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Trend params) {
        trendService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        trendService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Trend params) {
        trendService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
