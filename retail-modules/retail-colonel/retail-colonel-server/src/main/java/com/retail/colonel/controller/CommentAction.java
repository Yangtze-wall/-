package com.retail.colonel.controller;

import com.retail.colonel.domain.Comment;
import com.retail.colonel.domain.vo.CommentVo;
import com.retail.colonel.service.CommentService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 团长动态评论表	 前端控制器
 * </p>
 *
 * @author
 * @since 2023-03-26
 */
@Controller
@RequestMapping("/comment")
public class CommentAction {


    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/list")
    public Result<List<Comment>> list(@RequestBody CommentVo commentVo) {

        List<Comment> list = commentService.list();
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Comment params) {
        commentService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        commentService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody Comment params) {
        commentService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
