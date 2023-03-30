package com.retail.colonel.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelApplyMapper;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.service.ColonelApplyService;

import javax.servlet.http.HttpServletRequest;


@Service("colonelApplyService")
public class ColonelApplyServiceImpl extends ServiceImpl<ColonelApplyMapper, ColonelApplyEntity> implements ColonelApplyService {

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
    public Result colonelApplyInsert(ColonelApplyEntity colonelApplyEntity) {
        colonelApplyEntity.setUserId(userInfo().getId());
        int insert = baseMapper.insert(colonelApplyEntity);
        if (insert<=0){
            return Result.error();
        }
        return Result.success("团长申请添加成功");
    }

    @Override
    public List<ColonelApplyEntity> select() {
        return baseMapper.selectList(new QueryWrapper<ColonelApplyEntity>());
    }

    @Override
    public Result coloneApplyUpdate(ColonelApplyEntity colonelApplyEntity) {
        baseMapper.updateById(colonelApplyEntity);
        return Result.success();
    }

    @Override
    public Result<ColonelApplyEntity> findByIdColoneApplyEntity(Long id) {
        ColonelApplyEntity colonelApplyEntity = baseMapper.selectOne(new QueryWrapper<ColonelApplyEntity>().lambda().eq(ColonelApplyEntity::getId, id));
        return Result.success(colonelApplyEntity);
    }

    @Override
    public Result deleteColonelApplyEntity(Long id) {
         baseMapper.delete(new QueryWrapper<ColonelApplyEntity>().lambda().eq(ColonelApplyEntity::getId,id));
        return Result.success();
    }


}
