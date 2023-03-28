package com.retail.colonel.controller;


import com.retail.colonel.domain.GroupConfig;
import com.retail.colonel.service.GroupConfigService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 团购配置表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/config")
public class GroupConfigAction {


    @Autowired
    private GroupConfigService groupConfigService;

    @GetMapping(value = "/list")
    public Result<List<GroupConfig>> list(){
        List<GroupConfig> list = groupConfigService.list();
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public Result<GroupConfig> getById(@PathVariable("id") String id) {
        return Result.success(groupConfigService.getById(id));
    }

    @PostMapping(value = "/create")
    public Result<Object> create(@RequestBody GroupConfig params) {
        groupConfigService.save(params);
        return Result.success();
    }

    @PostMapping(value = "/delete/{id}")
    public Result<Object> delete(@PathVariable("id") String id) {
        groupConfigService.removeById(id);
        return Result.success();    }

    @PostMapping(value = "/update")
    public Result<Object> delete(@RequestBody GroupConfig params) {
        groupConfigService.updateById(params);
        return Result.success();    }
}
