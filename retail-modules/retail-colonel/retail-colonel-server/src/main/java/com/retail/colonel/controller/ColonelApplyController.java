package com.retail.colonel.controller;

import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.service.ColonelApplyService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


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
    //团长申请添加
    @PostMapping("colonelApply")
    public Result colonelApply(@RequestBody ColonelApplyEntity colonelApplyEntity){
        colonelApplyEntity.setCreateTime(new Date());
        colonelApplyEntity.setStatus(0);
        colonelApplyService.save(colonelApplyEntity);
        return Result.success(null,"申请成功,等待管理员审核,注意查收邮件");
    }
}
