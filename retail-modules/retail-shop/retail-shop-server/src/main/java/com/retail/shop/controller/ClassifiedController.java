package com.retail.shop.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.shop.domain.ClassifiedEntity;
import com.retail.shop.domain.vo.ClassifiedVo;
import com.retail.shop.service.ClassifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 商品分类表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/classified")
public class ClassifiedController {

    @Autowired
    private ClassifiedService classifiedService;

    /**
     * 分类递归
     * @return
     */
    @PostMapping("/selectClassifiedTreeList")
    public Result<List<ClassifiedVo>> selectClassifiedTreeList(){
        List<ClassifiedVo> classifiedVoList = classifiedService.selectClassifiedTreeList();

        return Result.success(classifiedVoList);
    }

    /**
     * 添加
     * @param classifiedEntity
     * @return
     */
    @PostMapping("/insertClassified")
    public Result insertClassified(@RequestBody ClassifiedEntity classifiedEntity){
        return classifiedService.insertClassified(classifiedEntity);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deleteClassifiedById/{id}")
    public Result deleteClassifiedById(@PathVariable("id") Long id){
        return classifiedService.deleteClassifiedById(id);
    }

    /**
     * 修改
     * @param classifiedEntity
     * @return
     */
    @PostMapping("/updateClassified")
    public Result updateClassified(@RequestBody ClassifiedEntity classifiedEntity){
        return classifiedService.updateClassified(classifiedEntity);
    }


    /**
     * 父级
     * @return
     */
    @PostMapping("/selectClassifiedParent")
    public Result<List<ClassifiedEntity>>  selectClassifiedParent(){
        List<ClassifiedEntity> classifiedEntityList=classifiedService.selectClassifiedParent();

        return Result.success(classifiedEntityList);
    }

    /**
     * 子级
     * @param parentId
     * @return
     */
    @PostMapping("/selectClassifiedChildById/{parentId}")
    public Result<List<ClassifiedEntity>>  selectClassifiedChildById(@PathVariable("parentId") Long parentId){
        List<ClassifiedEntity> classifiedEntityList= classifiedService.selectClassifiedChildById(parentId);

        return Result.success(classifiedEntityList);
    }




}
