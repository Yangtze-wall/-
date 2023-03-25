package com.retail.colonel.controller;

import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.domain.request.ColonelEntryPowerRequest;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.retail.colonel.service.ColonelApplyService;


/**
 * 团长申请表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("colonel/colonelapply")
public class ColonelApplyController {

    @Autowired
    private ColonelApplyService colonelApplyService;


    /**
     * 团长申请表 分页
     * @param colonelEntryPowerRequest
     * @return
     */
    @PostMapping("/getColinelApplyList")
    public Result<PageResult<ColonelApplyEntity>> list(@RequestBody ColonelEntryPowerRequest colonelEntryPowerRequest){
        PageHelper.startPage(colonelEntryPowerRequest.getPageNum(),colonelEntryPowerRequest.getPageSize());
        List<ColonelApplyEntity> list=colonelApplyService.select();
        PageInfo<ColonelApplyEntity> colonelApplyEntityPageInfo = new PageInfo<>(list);
        return PageResult.toResult(colonelApplyEntityPageInfo.getTotal(),list);
    }


    /**
     * 团长申请表 添加
     * @param colonelApplyEntity
     * @return
     */
    @PostMapping("/colonelApplyInsert")
    public Result colonelApplyInsert(@RequestBody ColonelApplyEntity colonelApplyEntity){
        return colonelApplyService.colonelApplyInsert(colonelApplyEntity);
    }




    /**
     * 团长申请表 修改
     * @param colonelApplyEntity
     * @return
     */
    @PutMapping("/coloneApplyUpdate")
    public Result coloneApplyUpdate(@RequestBody ColonelApplyEntity colonelApplyEntity){
        return colonelApplyService.coloneApplyUpdate(colonelApplyEntity);
    }

    /**
     * 团长申请表回显
     * @param id
     * @return
     */
    @GetMapping("/findByIdColoneApplyEntity/{id}")
    public Result<ColonelApplyEntity> findByIdColoneApplyEntity(@PathVariable("id") Long id){
        return colonelApplyService.findByIdColoneApplyEntity(id);
    }

    /**
     * 团长申请表 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteColonelApplyEntity(@PathVariable("id") Long id){
        return colonelApplyService.deleteColonelApplyEntity(id);
    }
}
