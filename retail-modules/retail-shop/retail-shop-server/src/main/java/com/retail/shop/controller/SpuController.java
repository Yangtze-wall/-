package com.retail.shop.controller;


import com.retail.common.domain.vo.SpuVo;
import com.retail.common.result.Result;
import com.retail.shop.domain.SpuEntity;
import com.retail.shop.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("/shop/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/selectListSpu")
    public Result<List<SpuEntity>> selectListSpu(){

       List<SpuEntity> spuEntityList= spuService.selectListSpu();

       return Result.success(spuEntityList);
    }

    @PostMapping("/selectSpu")
    public SpuVo selectSpu(@RequestParam("spuId")Long spuId){
        SpuVo spuVo=   spuService.selectSpu(spuId);
        return spuVo;
    }
}
