package com.retail.bargain.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.retail.bargain.domain.request.SeckillRequest;
import com.retail.bargain.vo.SeckillEntityVo;
import com.retail.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.bargain.mapper.SeckillMapper;
import com.retail.bargain.domain.SeckillEntity;
import com.retail.bargain.service.SeckillService;

import javax.annotation.Resource;


@Service("seckillService")
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillEntity> implements SeckillService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private SeckillMapper seckillMapper;

    @Override
    public List<SeckillEntityVo> seckillList(SeckillRequest seckillRequest) {
        //从redis里拿到秒杀商品数据
        String seckillList = redisTemplate.opsForValue().get("seckill_list");
        List<SeckillEntity> seckillEntities = JSONObject.parseArray(seckillList, SeckillEntity.class);
        //时间区间查询/stream过滤
        List<SeckillEntity> seckillEntityListVo = seckillEntities.stream().filter(n -> seckillRequest.getTime().getTime() > n.getSeckillStartTime().getTime()
                && seckillRequest.getTime().getTime() < n.getSeckillEndTime().getTime()).collect(Collectors.toList());
        if (seckillEntityListVo != null) {
            //时间区间查询
            List<SeckillEntityVo> selectList = this.selectList(seckillEntityListVo, seckillRequest);

            return selectList;
        }
        //如果redis没有就从数据库查找
        List<SeckillEntity> seckillEntityList = seckillMapper.selectList(null);
        //找到存redis
        if (seckillEntityList != null && seckillEntityList.size() > 0) {
            redisTemplate.opsForValue().set("seckill_list", JSONObject.toJSONString(seckillEntityList), 2, TimeUnit.HOURS);
            //时间区间查询/stream过滤
            List<SeckillEntity> seckillEntityListVoo = seckillEntityList.stream().filter(n -> seckillRequest.getTime().getTime() > n.getSeckillStartTime().getTime()
                    && seckillRequest.getTime().getTime() < n.getSeckillEndTime().getTime()).collect(Collectors.toList());
            if (seckillEntityListVoo != null) {
                //时间区间查询
                List<SeckillEntityVo> seckillEntityVos = this.selectList(seckillEntityListVoo, seckillRequest);
                seckillEntityVos.stream().sorted().collect(Collectors.toList());
                return seckillEntityVos;
            }
        }
        return null;
    }

    public List<SeckillEntityVo> selectList(List<SeckillEntity> list, SeckillRequest seckillRequest) {
        List<SeckillEntity> seckillEntityListVoo = list.stream().filter(n -> seckillRequest.getTime().getTime() > n.getSeckillStartTime().getTime()
                && seckillRequest.getTime().getTime() < n.getSeckillEndTime().getTime()).collect(Collectors.toList());
        //每一件商品的剩余百分比
        List<SeckillEntityVo> collect = seckillEntityListVoo.stream().map(c -> {
            SeckillEntityVo seckillEntityVo = new SeckillEntityVo();
            //剩余库存/秒杀库存 = 百分比
            seckillEntityVo.setPercent(NumberUtil.div(c.getSeckillResidueCount(), c.getSeckillCount()));

            seckillEntityVo.setId(c.getId());
            seckillEntityVo.setStatus(c.getStatus());
            seckillEntityVo.setShopId(c.getShopId());
            seckillEntityVo.setSeckillCount(c.getSeckillCount());
            seckillEntityVo.setSeckillLimit(c.getSeckillLimit());
            seckillEntityVo.setSeckillPrice(c.getSeckillPrice());
            seckillEntityVo.setSeckillEndTime(c.getSeckillEndTime());
            seckillEntityVo.setSeckillConfigId(c.getSeckillConfigId());
            seckillEntityVo.setSeckillStartTime(c.getSeckillStartTime());
            seckillEntityVo.setSeckillResidueCount(c.getSeckillResidueCount());
            seckillEntityVo.setSeckillCreateTime(c.getSeckillCreateTime());
            seckillEntityVo.setSlideshow(c.getSlideshow());
            seckillEntityVo.setSeckillHeadline(c.getSeckillHeadline());
            seckillEntityVo.setPostFree(c.getPostFree());
            seckillEntityVo.setSeckillIntroduce(c.getSeckillIntroduce());
            return seckillEntityVo;
        }).collect(Collectors.toList());
        return collect;
    }
}
