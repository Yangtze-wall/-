package com.retail.bargain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.PayRequest;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.domain.request.StartSeckill;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.Result;

import java.util.List;

public interface SeckillService extends IService<SeckillEntity> {

//    /**
//     * 秒杀商品详情
//     * @param id
//     * @return
//     */
//    Result<SeckillSpuVo> itemDetail(Long id);

    /**
     * 秒杀商品页面
     * @param request
     * @return
     */
    List<SeckillEntity> seckillList(SeckillRequest request);

    /**
     * 秒杀商品添加
     * @param entity
     * @return
     */
    Result seckillAdd(SeckillEntity entity);

    /**
     * 点击开始秒杀
     * @param seckill
     * @return
     */
    Result start(StartSeckill seckill);

    /**
     * 支付
     * @param payRequest
     * @return
     */
    Result pay(PayRequest payRequest);

    /**
     * 商品详情信息
     * @param id
     * @return
     */
    SeckillSpuVo itemDetail(Long id);
}
