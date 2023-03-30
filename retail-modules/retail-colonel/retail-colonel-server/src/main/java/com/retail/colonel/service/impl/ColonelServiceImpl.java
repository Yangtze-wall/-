package com.retail.colonel.service.impl;

import com.alibaba.fastjson.JSON;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelMapper;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.service.ColonelService;

import javax.servlet.http.HttpServletRequest;


@Service("colonelService")
public class ColonelServiceImpl extends ServiceImpl<ColonelMapper, ColonelEntity> implements ColonelService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }
    @Override
    public Result coloneInsert(ColonelEntity colonelEntity) {
        colonelEntity.setUserId(userInfo().getId());
        int insert = baseMapper.insert(colonelEntity);
        if (insert<=0){
            return Result.error();
        }
        return Result.success("团长添加成功");
    }

    @Override
    public List<ColonelEntity> select() {
        return baseMapper.selectList(new QueryWrapper<ColonelEntity>());
    }

    @Override
    public Result coloneUpdate(ColonelEntity colonelEntity) {
        baseMapper.updateById(colonelEntity);
        return Result.success();
    }

    @Override
    public Result<ColonelEntity> findByIdColonelEntity(Long id) {
        ColonelEntity colonelEntity = baseMapper.selectOne(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getId, id));
        return Result.success(colonelEntity);
    }

    @Override
    public Result deleteColonelEntity(Long id) {
        baseMapper.delete(new QueryWrapper<ColonelEntity>().lambda().eq(ColonelEntity::getId,id));
        return Result.success();
    }
}
