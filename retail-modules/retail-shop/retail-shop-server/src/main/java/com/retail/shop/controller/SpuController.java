package com.retail.shop.controller;


import com.retail.common.result.Result;
import com.retail.common.utils.OssUtil;
import com.retail.shop.domain.*;
import com.retail.shop.domain.vo.SearchParam;
import com.retail.shop.domain.vo.SkuModelVo;
import com.retail.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.directory.SearchResult;
import java.util.List;


/**
 * 商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping("/insertSpu")
    public Result insertSpu(@RequestBody SpuEntity spuEntity){

        return spuService.insertSpu(spuEntity);
    }

    @PostMapping("/deleteSpuById/{id}")
    public Result deleteSpuById(@PathVariable("id")Long id){
        return spuService.deleteSpuById(id);
    }

    @PostMapping("/updateSpu")
    public Result updateSpu(@RequestBody SpuEntity spuEntity){
        return spuService.updateSpu(spuEntity);
    }



}
