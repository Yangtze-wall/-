package com.retail.colonel.controller;

import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
     * 申请团长
     * @param colonelApplyEntity
     * @return
     */

    @PostMapping("/addColonelApply")
    public Result colonelApplyService(@RequestBody ColonelApplyEntity colonelApplyEntity){


        if (colonelApplyEntity==null){
            return Result.error(502,"申请条件为空");
        }

      Result result=  colonelApplyService.colonelApplyService(colonelApplyEntity);

        return Result.success(result);

    }

    /**
     * 团长佣金流水
     * @param commissionVo
     * @return
     */
    @PostMapping("/selectCommission")
    public Result<PageResult<CommissionVo>> selectCommission(@RequestBody CommissionVo commissionVo){

        PageHelper.startPage(commissionVo.getPageNum(),commissionVo.getPageSize());

        List<CommissionVo> commissionVoList=colonelApplyService.selectCommission();

        PageInfo<CommissionVo> pageInfo = new PageInfo<>(commissionVoList);

        return PageResult.toResult(pageInfo.getTotal(),commissionVoList);


    }


}
