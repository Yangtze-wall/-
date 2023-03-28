package com.retail.colonel.controller;


import com.retail.colonel.domain.RouteEntity;
import com.retail.colonel.service.RouteService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团长路线表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @GetMapping(value = "/list")
    public Result<List<RouteEntity>> list(){
        List<RouteEntity> list = routeService.list();
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public Result<RouteEntity> getById(@PathVariable("id") String id) {
      return   Result.success(routeService.getById(id));
    }

    @PostMapping(value = "/create")
    public Result<Object> create(@RequestBody RouteEntity params) {
        routeService.save(params);
        return Result.success(null,"成功");
    }

    @DeleteMapping(value = "/delete/{id}")
    public Result<Object> delete(@PathVariable("id") String id) {
        routeService.removeById(id);
        return Result.success(null,"成功");
    }

    @PutMapping(value = "/update")
    public Result<Object> delete(@RequestBody RouteEntity params) {
        routeService.updateById(params);
        return Result.success(null,"成功");
    }
}
