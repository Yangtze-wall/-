package com.retail.colonel.controller;

import com.alibaba.fastjson.JSON;
import com.retail.colonel.domain.Team;
import com.retail.colonel.service.TeamService;
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
 * 拼团表 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-27
 */
@RestController
@RequestMapping("/team")
public class TeamAction {


    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/list")
    public Result<List<Team>> list()  {
        List<Team> list = teamService.list();
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public Result<Team> getById(@PathVariable("id") String id) {
        return Result.success(teamService.getById(id));
    }

    @PostMapping(value = "/create")
    public Result create(@RequestBody Team params) {
        params.setTeamStartTime(new Date());
        teamService.save(params);
        return Result.success(null,"成功");
    }

    @PostMapping(value = "/delete/{id}")
    public Result<Object> delete(@PathVariable("id") String id) {
        teamService.removeById(id);
        return Result.success(null,"成功");
    }

    @PostMapping(value = "/update")
    public Result<Object> delete(@RequestBody Team params) {
        teamService.updateById(params);
        return Result.success(null,"成功");
    }
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //拼团邀请接口
    @GetMapping("applyUser/{id}")
    public Result applyUser(@PathVariable("id") String id){
        String token = request.getHeader("token");
        String userKey = JwtUtils.getUserKey(token);
        String s = redisTemplate.opsForValue().get(TokenConstants.LOGIN_TOKEN_KEY + userKey);
        UserEntityVo user = JSON.parseObject(s, UserEntityVo.class);
        Result result = teamService.applyUser(user.getId(),id);
        return result;
    }
}
