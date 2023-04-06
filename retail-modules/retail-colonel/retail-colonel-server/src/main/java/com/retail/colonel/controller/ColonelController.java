package com.retail.colonel.controller;

import java.util.HashSet;
import java.util.List;


import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.domain.request.ColonelEntryPowerRequest;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.retail.colonel.service.ColonelService;


/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("colonel/colonel")
public class ColonelController {

    @Autowired
    private ColonelService colonelService;

    /**
     * 列表 分页
     * @param colonelEntryPowerRequest
     * @return
     */
    @PostMapping("/getColinelList")
    public Result<PageResult<ColonelEntity>> list(@RequestBody ColonelEntryPowerRequest colonelEntryPowerRequest){
        PageHelper.startPage(colonelEntryPowerRequest.getPageNum(),colonelEntryPowerRequest.getPageSize());
        List<ColonelEntity> list=colonelService.select();
        PageInfo<ColonelEntity> colonelEntityPageInfo = new PageInfo<>(list);
        return PageResult.toResult(colonelEntityPageInfo.getTotal(),list);
    }


    /**
     * 团长表 添加
     * @param colonelEntity
     * @return
     */
    @PostMapping("/coloneInsert")
    public Result coloneInsert(@RequestBody ColonelEntity colonelEntity){
        return colonelService.coloneInsert(colonelEntity);
    }
    /**
     * 团长表 修改
     * @param colonelEntity
     * @return
     */
    @PutMapping("/coloneUpdate")
    public Result coloneUpdate(@RequestBody ColonelEntity colonelEntity){
        return colonelService.coloneUpdate(colonelEntity);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("/findByIdColonelEntity/{id}")
    public Result<ColonelEntity> findByIdColonelEntity(@PathVariable("id") Long id){
        return colonelService.findByIdColonelEntity(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteColonelEntity(@PathVariable("id") Long id){
        return colonelService.deleteColonelEntity(id);
    }




}
