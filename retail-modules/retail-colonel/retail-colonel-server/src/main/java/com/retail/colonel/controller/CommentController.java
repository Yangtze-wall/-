package com.retail.colonel.controller;

import com.retail.colonel.domain.Comment;
import com.retail.colonel.service.CommentService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 团长动态评论表	 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
//根据状态id 查看不同的评论
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;
    @Autowired
    private HttpServletRequest request;
    @PostMapping("/list")
    public Result<List<Comment>> list() {
        List<Comment> list = commentService.show();
        Result<List<Comment>> success = Result.success(list);
        return success;
    }

    @GetMapping(value = "/{id}")
    public Result<Comment> getById(@PathVariable("id") String id) {
        return Result.success(commentService.getById(id));
    }

    @PostMapping(value = "/create")
    public Result<Object> create(@RequestBody Comment params) {
        commentService.save(params);
        return Result.success();
    }

    @PostMapping(value = "/delete/{id}")
    public Result<Object> delete(@PathVariable("id") String id) {
        commentService.removeById(id);
        return Result.success();    }

    @PostMapping(value = "/update")
    public Result<Object> delete(@RequestBody Comment params) {
        commentService.updateById(params);
        return Result.success();    }
}
