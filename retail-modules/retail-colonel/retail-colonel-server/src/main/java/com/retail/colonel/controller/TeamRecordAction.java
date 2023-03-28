package com.retail.colonel.controller;

import com.retail.colonel.domain.TeamRecord;
import com.retail.colonel.service.TeamRecordService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 拼团记录表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/teamRecord")
public class TeamRecordAction {


    @Autowired
    private TeamRecordService teamRecordService;

    @GetMapping(value = "/")
    public Result<List<TeamRecord>> list() {
        List<TeamRecord> list = teamRecordService.list();
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public Result<TeamRecord> getById(@PathVariable("id") String id) {
        return Result.success(teamRecordService.getById(id));
    }

    @PostMapping(value = "/create")
    public Result<Object> create(@RequestBody TeamRecord params) {
        teamRecordService.save(params);
        return Result.success(null,"成功");
    }

    @PostMapping(value = "/delete/{id}")
    public Result<Object> delete(@PathVariable("id") String id) {
        teamRecordService.removeById(id);
        return Result.success(null,"success");
    }

    @PostMapping(value = "/update")
    public Result<Object> delete(@RequestBody TeamRecord params) {
        teamRecordService.updateById(params);
        return Result.success(null,"success");
    }


}
