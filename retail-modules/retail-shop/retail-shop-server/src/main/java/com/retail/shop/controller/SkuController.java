package com.retail.shop.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.shop.domain.request.GoodsRequest;
import com.retail.shop.service.SkuService;
import com.retail.shop.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 商品列表
     */
    @PostMapping("list")
    public Result<PageResult<GoodsVo>> list(@RequestBody GoodsRequest request) {
        //分页
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<GoodsVo> list = skuService.goodsList(request);
        PageInfo<GoodsVo> pageInfo = new PageInfo<>(list);
        Result<PageResult<GoodsVo>> result = PageResult.toResult(pageInfo.getTotal(), list);
        return result;
    }

//    /**
//     * 我的优惠券 TODO
//     */
//    @PostMapping("coupon/{userId}")
//    public Result<List<CouponVo>>



}
