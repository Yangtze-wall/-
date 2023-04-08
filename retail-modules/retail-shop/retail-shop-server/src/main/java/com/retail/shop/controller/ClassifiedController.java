package com.retail.shop.controller;

import java.util.List;


import com.retail.common.result.Result;
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
    @PostMapping("lectClassifiedTreeList")
    public Result<List<ClassifiedVo>> selectClassifiedTreeList(){
        List<ClassifiedVo> classifiedVoList = classifiedService.selectClassifiedTreeList();
        return Result.success(classifiedVoList);
    }

}
