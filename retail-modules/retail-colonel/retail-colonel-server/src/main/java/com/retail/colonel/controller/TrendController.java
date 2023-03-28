package com.retail.colonel.controller;


import com.alibaba.fastjson.JSON;
import com.retail.colonel.domain.Trend;
import com.retail.colonel.domain.vo.TrendCommentVo;
import com.retail.colonel.service.TrendService;
import com.retail.common.constant.TokenConstants;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 团长动态表
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@RestController
@RequestMapping("/trend")
public class TrendController {

    @Autowired
    private TrendService trendService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //根据自己的id查看自己的动态
    @PostMapping(value = "/list")
    public Result<List<Trend>> list(){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo userInfo = JSON.parseObject(s, UserEntityVo.class);
        List<Trend> list = trendService.selectTrend(userInfo.getId());
        return Result.success(list);
    }
    //通过动态的编号查看下面的评论数
    @GetMapping(value = "/{id}")
    public Result<TrendCommentVo> getById(@PathVariable("id") String id) {
        TrendCommentVo trendCommentVo = trendService.selectList(id);
        return Result.success(trendCommentVo);
    }
    //新增动态
    @PostMapping(value = "/create")
    public Result create(@RequestBody Trend params) {
        params.setCreateTime(new Date());
        trendService.save(params);
        return Result.success(null,"添加成功");
    }
    //团长删除动态
    @PostMapping(value = "/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        trendService.removeById(id);
        return Result.success(null,"删除成功");
    }
}
