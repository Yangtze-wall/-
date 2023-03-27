package com.retail.colonel.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.SeckillEntity;
import com.retail.colonel.mapper.SeckillMapper;
import com.retail.colonel.service.SeckillService;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.domain.vo.SkuInventoryVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.colonel.service.impl
 * @ClassName:      SeckillServiceImpl
 * @Author:     2766395184
 * @Description:  秒杀 逻辑层
 * @Date:    2023/3/25 9:57
 * @Version:    1.0
 */
@Service("seckillService")
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillEntity> implements SeckillService {





    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public Result<SeckillSpuVo> itemDetail(Long id) {

        //查询商品(拼团开始时间)
        String s = redisTemplate.opsForValue().get("seckill_spu" + id);
        SeckillSpuVo seckillSpuVo = JSON.parseObject(s, SeckillSpuVo.class);
        //查询库存
        SkuInventoryVo skuInventoryVo=seckillMapper.selectSkuInventory(seckillSpuVo.getSpuId());




        return Result.success(seckillSpuVo);
    }





}
