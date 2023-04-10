package com.retail.colonel.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.retail.colonel.bean.Face;
import com.retail.colonel.domain.ColonelEntity;
import com.retail.colonel.domain.Info;
import com.retail.colonel.domain.result.R;
import com.retail.colonel.domain.vo.ApplyVo;
import com.retail.colonel.domain.vo.ColonelInfoVo;
import com.retail.colonel.service.ColonelService;
import com.retail.colonel.service.FaceService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 团长表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 10:09:39
 */
@RestController
@RequestMapping("colonel/")
public class ColonelController {
    @Autowired
    private ColonelService colonelService;
    //团长的个人主页
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String > redisTemplate;
    @GetMapping("colonelInfo")
    public Result<ColonelInfoVo> colonelInfo(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        ColonelEntity colonelEntity =colonelService.findById(user.getId());
        ColonelInfoVo colonelInfoVo = new ColonelInfoVo();
        colonelInfoVo.setUser(user);
        colonelInfoVo.setColonel(colonelEntity);
        return Result.success(colonelInfoVo);
    }
    //佣金
    @GetMapping("commissionInfo")
    public Result<ColonelEntity> commission(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        ColonelEntity colonelEntity =  colonelService.getInfo(user.getId());
        return Result.success(colonelEntity);
    }
    @GetMapping("/colonel/findById/{id}")
    ColonelEntity findById(@PathVariable("id") Long id){
        return colonelService.getInfo(id);
    }
    @Autowired
    private FaceService faceService;
    @PostMapping("apply")
    public Result apply(@RequestBody ApplyVo applyVo) {
        ColonelEntity colonelEntity = new ColonelEntity();

        Map<String, Object> params = new HashMap<>();
        params.put("idCardNo", applyVo.getCord());
        params.put("name", applyVo.getName());
        String result = HttpUtil.createPost("https://jumdata.market.alicloudapi.com/idcard/validate")
                .header("Authorization", "APPCODE 3db9cf10c61f4ee18708b2b233087869")
                .form(params).execute().body();
        R result1 = JSON.parseObject(result, R.class);
        Info info = result1.getData();
        colonelEntity.setAddress(info.getAddress());
        colonelEntity.setCreateTime(new Date());
        colonelEntity.setMonthCommission(new BigDecimal(0));
        colonelEntity.setSurplusCommission(new BigDecimal(0));
        colonelEntity.setTotalCommission(new BigDecimal(0));
        colonelEntity.setStatus(1);
        colonelEntity.setGradeId(1L);
        colonelService.save(colonelEntity);
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        user.setStatus(1);
        redisTemplate.opsForValue().set(TokenConstants.LOGIN_TOKEN_KEY+userKey,
                JSON.toJSONString(user));
        Face face = new Face();
        //添加脸部的一些验证
        face.setColonelId(colonelEntity.getId());
        face.setFaceBase(applyVo.getBase64());
        face.setVefNum(1);
        face.setCreateTime(new Date());
        faceService.save(face);
        return Result.success(null,"已成功申请 等待团长管理员审核");
    }

}
