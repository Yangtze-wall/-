package com.retail.bargain.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.bargain.domain.SeckillConfig;
import com.retail.bargain.domain.request.SeckillConfigRequest;
import com.retail.bargain.mapper.SeckillConfigMapper;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.retail.bargain.service.SeckillConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    @Resource
    private SeckillConfigMapper seckillConfigMapper;


    /**
     * 配置表 列表
     */
    @GetMapping(value = "/list")
    public Result<PageResult<SeckillConfig>> list(@RequestBody SeckillConfigRequest request){
        //分页
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        //根据状态  配置名称查询
        List<SeckillConfig> seckillConfigs = seckillConfigMapper.selectList(new QueryWrapper<SeckillConfig>().lambda()
                .like(StrUtil.isNotEmpty(request.getSeckillConfigName()), SeckillConfig::getSeckillConfigName, request.getSeckillConfigName())
                .eq(SeckillConfig::getStatus, request.getStatus())
        );
        PageInfo<SeckillConfig> pageInfo = new PageInfo<>(seckillConfigs);
        Result<PageResult<SeckillConfig>> result = PageResult.toResult(pageInfo.getTotal(), seckillConfigs);
        return result;
    }

    /**
     * 配置表 id查
     */
    @GetMapping(value = "findById/{id}")
    public Result<SeckillConfig> getById(@PathVariable("id") String id) {
        SeckillConfig byId = seckillConfigService.getById(id);
        if (byId!=null) {
            return Result.success(byId);
        }
        return Result.error("查找失败");
    }

    /**
     * 配置表 增
     */
    @PostMapping(value = "/create")
    public Result<String> create(@RequestBody SeckillConfig params) {
        //seckillConfigCreateTime  秒杀创建时间为系统时间
        params.setSeckillConfigCreateTime(new Date());
        boolean save = seckillConfigService.save(params);
        if (save){
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }
    /**
     * 配置表 id删除
     */
    @PostMapping(value = "/delete/{id}")
    public Result<String> delete(@PathVariable("id") String id) {
        boolean b = seckillConfigService.removeById(id);
        if (b){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
    /**
     * 配置表 修改
     */
    @PostMapping(value = "/update")
    public Result<String> delete(@RequestBody SeckillConfig params) {
        boolean update = seckillConfigService.updateById(params);
        if (update){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }
}
