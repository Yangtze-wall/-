package com.retail.shop.controller;


import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.common.utils.OssUtil;
import com.retail.common.domain.vo.ProductVo;
import com.retail.shop.domain.vo.SearchParam;
import com.retail.shop.domain.vo.SkuModelVo;
import com.retail.shop.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 商品 sku 表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 17:00:28
 */

@RestController
@RequestMapping("shop/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/findAll")
    public Result<List<SkuModelVo>> findAll(){
        List<SkuModelVo> list = skuService.findAll();
        return Result.success(list);
    }
    @PostMapping("/findSkuList")
    public Result<List<ProductVo>> findSkuList(){
        List<ProductVo> list = skuService.findSkuList();
        return Result.success(list);
    }

    @PostMapping("/search")
    public Result<PageResult<SkuModelVo>> SkuModelVoList(@RequestBody SearchParam searchParam){

        Result<PageResult<SkuModelVo>> result=  skuService.search(searchParam);
        return result;
    }
    @PostMapping("/insertSku")
    public Result insertSku(@RequestBody ProductVo productVo){

        return skuService.insertSku(productVo);
    }

    @PostMapping("/deleteSkuById/{id}")
    public Result deleteSkuById(@PathVariable("id")Long id){
        return skuService.deleteSkuById(id);
    }
    @PostMapping("/findBySkuId/{id}")
    public Result<ProductVo> findBySkuId(@PathVariable("id")Long id){
        return skuService.findBySkuId(id);
    }

    @PostMapping("/updateSku")
    public Result updateSku(@RequestBody ProductVo productVo){
        return skuService.updateSku(productVo);
    }
    @RequestMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile multipartFile){

        String s = OssUtil.uploadMultipartFile(multipartFile);
        System.out.println(s);
        return Result.success(s);
    }



}
