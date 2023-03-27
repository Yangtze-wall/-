package com.retail.colonel.job;

import com.alibaba.fastjson.JSON;
import com.retail.colonel.domain.SeckillEntity;
import com.retail.colonel.mapper.SeckillMapper;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName:    retail-cloud
 * @Package:        com.retail.colonel.job
 * @ClassName:      RedisSeckillJob
 * @Author:     2766395184
 * @Description:  秒杀商品 定时存储
 * @Date:    2023/3/25 10:57
 * @Version:    1.0
 */
@Component
public class RedisSeckillJob {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Scheduled(cron = "0/3 * * * * *")
    public Result task(){
         List<SeckillSpuVo>  seckillSpuVo=seckillMapper.selectSpu();

        System.out.println("<<<<<<<"+seckillSpuVo);

        List<SeckillSpuVo> seckillSpuVos = seckillSpuVo.stream().filter(c ->c.getEsRedis()  == 1).collect(Collectors.toList());

        System.out.println("<<<<<<<seckillSpuVos"+seckillSpuVos);
        if (seckillSpuVos==null){
            return Result.success("商品已全部同步");
        }

        seckillSpuVos.stream().forEach(c->{
            SeckillEntity seckillEntity = new SeckillEntity();
            redisTemplate.opsForValue().set("seckill_spu"+c.getId(), JSON.toJSONString(c));
            seckillEntity.setId(c.getId());
            seckillEntity.setEsRedis(0);
            seckillMapper.updateEsRedis(seckillEntity);
        });

        return Result.success("同步结束");
    }














}
