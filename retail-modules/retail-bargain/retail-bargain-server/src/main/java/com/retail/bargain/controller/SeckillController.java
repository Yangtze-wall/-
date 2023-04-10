package com.retail.bargain.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.PaymentEntity;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.domain.request.StartSeckill;
import com.retail.bargain.service.SeckillService;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import com.retail.common.utils.DESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: retail-cloud
 * @Package: com.retail.colonel.controller
 * @ClassName: SeckillController
 * @Author: 2766395184
 * @Description: 秒杀控制层
 * @Date: 2023/3/25 9:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("colonel/seckill")
@RequiredArgsConstructor
public class SeckillController {


    @Resource
    private SeckillService seckillService;
    @Value("${des.decryptKey}")
    private String decryptKey;


    /**
     * 秒杀首页 名称,时间区间查,分页
     *
     * @param cipherText
     * @return
     */
    @PostMapping("list")
    public Result<PageResult<SeckillEntity>> list(@RequestBody String cipherText) {
        //解密
        String decrypt = DESUtil.aesDecryptForFront(cipherText, decryptKey);
        SeckillRequest request = JSONObject.parseObject(decrypt, SeckillRequest.class);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SeckillEntity> list = seckillService.seckillList(request);
        PageInfo<SeckillEntity> pageInfo = new PageInfo<>(list);
        Result<PageResult<SeckillEntity>> result = PageResult.toResult(pageInfo.getTotal(), list);
        return result;
    }

    /**
     * 秒杀商品添加
     */
    @PostMapping("add")
    public Result<SeckillEntity> add(@RequestBody SeckillEntity entity) {
        Result result = seckillService.seckillAdd(entity);
        return result;
    }

//    /**
//     * 秒杀商品明细
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("itemDetail/{id}")
//    public Result<SeckillSpuVo> itemDetail(@PathVariable("id") Long id) {
//
//        if (id == null) {
//            Result.error(502, "请选择正确的秒杀商品");
//        }
//
//        Result<SeckillSpuVo> seckillEntity = seckillService.itemDetail(id);
//
//        return seckillEntity;
//    }

    /**
     * 根据id查询秒杀商品详情
     */
    @PostMapping("itemDetail/{id}")
    public Result<SeckillSpuVo> itemDetail(@PathVariable("id") Long spuId) {
       SeckillSpuVo spuVo =  seckillService.itemDetail(spuId);
       return Result.success(spuVo);
    }

    /**
     * 点击开始秒杀
     */

    @PostMapping("start")
    public Result<SeckillEntity> start(@RequestBody StartSeckill seckill) {
        Result result = seckillService.start(seckill);
        return result;
    }


    /**
     * 支付
     */
    public Result pay(@RequestBody PaymentEntity paymentEntity) {
        Result result = seckillService.pay(paymentEntity);
        return result;
    }


}
