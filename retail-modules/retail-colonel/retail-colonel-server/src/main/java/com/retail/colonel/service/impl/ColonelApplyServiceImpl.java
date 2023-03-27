package com.retail.colonel.service.impl;

import com.alibaba.fastjson.JSON;
import com.retail.colonel.domain.ColonelEntity;

import com.retail.colonel.fegin.UserFeign;
import com.retail.colonel.service.ColonelService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelApplyMapper;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.service.ColonelApplyService;

import javax.servlet.http.HttpServletRequest;


@Service("colonelApplyService")
public class ColonelApplyServiceImpl extends ServiceImpl<ColonelApplyMapper, ColonelApplyEntity> implements ColonelApplyService {


    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ColonelService colonelService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Result colonelApplyService(ColonelApplyEntity colonelApplyEntity) {

        UserEntityVo userEntityVo = this.userInfo();

       List<ColonelEntity> colonelEntityList=colonelService.selectColonel(userEntityVo);

       if (colonelEntityList!=null){
           return Result.error("当前用户已是团长");
       }

        colonelApplyEntity.setUserId(userEntityVo.getId());
        colonelApplyEntity.setStatus(1);
        colonelApplyEntity.setCreateTime(new Date());

        this.baseMapper.insert(colonelApplyEntity);



        return Result.success("团长申请成功");
    }


    @Override
    public List<CommissionVo> selectCommission() {
        UserEntityVo userEntityVo = this.userInfo();

       List<CommissionVo> commissionVoList=userFeign.selectCommission(userEntityVo);

        return commissionVoList;
    }



    public UserEntityVo userInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        return user;
    }
}
