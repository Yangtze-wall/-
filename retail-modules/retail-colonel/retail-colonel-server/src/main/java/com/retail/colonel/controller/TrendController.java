package com.retail.colonel.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.colonel.config.UserInfo;
import com.retail.colonel.domain.Trend;
import com.retail.colonel.domain.vo.TrendCommentVo;
import com.retail.colonel.service.TrendService;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    //根据自己的id查看自己的动态
    @PostMapping(value = "/list")
    public Result<PageResult<Trend>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        UserEntityVo userInfo = new UserInfo().getInfo(request);
        PageHelper.startPage(current, pageSize);
        List<Trend> list = trendService.selectTrend(userInfo.getId());
        PageInfo<Trend> trendPageInfo = new PageInfo<>(list);
        Result<PageResult<Trend>> pageResultResult = PageResult.toResult(trendPageInfo.getTotal(), list);
        return pageResultResult;
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
