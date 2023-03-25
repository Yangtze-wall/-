package com.retail.bargain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.vo.SeckillEntityVo;

import java.util.List;
import java.util.Map;

/**
 * 秒杀商品表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 11:22:38
 */
public interface SeckillService extends IService<SeckillEntity> {

    /**
     * 秒杀商品列表
     * @param seckillRequest
     * @return
     */
    List<SeckillEntityVo> seckillList(SeckillRequest seckillRequest);
}

