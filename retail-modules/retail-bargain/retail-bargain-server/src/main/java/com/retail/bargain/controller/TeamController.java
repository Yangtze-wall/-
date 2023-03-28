package com.retail.bargain.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.bargain.annotation.ApiAnnotation;
import com.retail.bargain.domain.TeamEntity;
import com.retail.bargain.service.TeamService;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 拼团表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
@RestController
@RequestMapping("bargain/TeamEntity")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/")
    public Result<PageResult<TeamEntity>> list(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<TeamEntity> list = teamService.list();
        PageInfo<TeamEntity> pageInfo = new PageInfo<>(list);
        return PageResult.toResult(pageInfo.getTotal(),list);
    }
    @ApiAnnotation("获取信息")
    @GetMapping(value = "/{id}")
    public Result<TeamEntity> getById(@PathVariable("id") String id) {
        TeamEntity byId = teamService.getById(id);
        return Result.success(byId);
    }

    @PostMapping(value = "/create")
    public Result create(@RequestBody TeamEntity params) {
        params.setTeamStartTime(new Date());
        teamService.save(params);
        return Result.success(null,"添加成功");
    }

    @PostMapping(value = "/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        teamService.removeById(id);
       return Result.success(null,"删除成功");
    }

    @PostMapping(value = "/update")
    public Result delete(@RequestBody TeamEntity params) {
        teamService.updateById(params);
        return Result.success(null,"修改成功");
    }
}
