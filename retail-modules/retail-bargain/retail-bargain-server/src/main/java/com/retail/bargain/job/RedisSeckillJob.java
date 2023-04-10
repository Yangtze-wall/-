package com.retail.bargain.job;

import com.alibaba.fastjson.JSONObject;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.mapper.SeckillMapper;
import com.retail.common.result.Result;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.bargain.job
 * @ClassName:      RedisSeckillJob
 * @Author:     2766395184
 * @Description:  秒杀商品 定时存储
 * @Date:    2023/3/25 10:57
 * @Version:    1.0
 */
@Component
public class RedisSeckillJob {

    @Resource
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @XxlJob("ss")
    public Result task(){
        System.out.println("黄仁翔");
        //查询出秒杀商品数据
        List<SeckillEntity> selectList = seckillMapper.selectList(null);
        //存入Redis
        redisTemplate.opsForValue().set("seckill_list", JSONObject.toJSONString(selectList),12, TimeUnit.HOURS);
//        List<SeckillSpuVo>  seckillSpuVo=seckillMapper.selectSpu();
//
//        System.out.println("<<<<<<<"+seckillSpuVo);
//
//        List<SeckillSpuVo> seckillSpuVos = seckillSpuVo.stream().filter(c ->c.getEsRedis()  == 1).collect(Collectors.toList());
//
//        System.out.println("<<<<<<<seckillSpuVos"+seckillSpuVos);
//        if (seckillSpuVos==null){
//            return Result.success("商品已全部同步");
//        }
//
//        seckillSpuVos.stream().forEach(c->{
//            SeckillEntity seckillEntity = new SeckillEntity();
//            redisTemplate.opsForValue().set("seckill_spu"+c.getId(), JSON.toJSONString(c));
//            seckillEntity.setId(c.getId());
//            seckillEntity.setEsRedis(0);
//            seckillMapper.updateEsRedis(seckillEntity);
//        });

        return Result.success("同步结束");
    }

















}
