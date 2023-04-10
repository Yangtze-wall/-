package com.retail.bargain.config;

import com.alibaba.fastjson.JSONObject;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.mapper.SeckillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.config
 * @Description TODO
 * @createTime 2023/3/26 14:43
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private SeckillMapper seckillMapper;
    /**
     * 项目启动时,秒杀商品数据存入Redis
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //查询出秒杀商品数据
        List<SeckillEntity> selectList = seckillMapper.selectList(null);
        //存入Redis
        redisTemplate.opsForValue().set("seckill_list", JSONObject.toJSONString(selectList),12, TimeUnit.HOURS);
    }

}
