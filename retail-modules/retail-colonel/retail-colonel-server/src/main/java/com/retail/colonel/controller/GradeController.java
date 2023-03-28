package com.retail.colonel.controller;

import com.retail.colonel.domain.GradeEntity;
import com.retail.colonel.service.GradeService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 团长等级
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    @PostMapping("list")
    public Result<List<GradeEntity>> list(){
        return Result.success(gradeService.list());
    }
    @GetMapping("/{id}")
    public Result<GradeEntity> getInfo(@PathVariable("id") Long id){
        return Result.success(gradeService.getById(id));
    }
    @PostMapping("add")
    public Result add(@RequestBody GradeEntity gradeEntity){
        gradeEntity.setCreateTime(new Date());
        gradeService.save(gradeEntity);
        return Result.success();
    }
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id){
        gradeService.removeById(id);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody GradeEntity gradeEntity){
        gradeService.updateById(gradeEntity);
        return Result.success();
    }
}
