package com.retail.colonel.controller;

import com.retail.colonel.config.UserInfo;
import com.retail.colonel.domain.Comment;
import com.retail.colonel.service.CommentService;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        UserEntityVo user = new UserInfo().getInfo(request);

        List<Comment> list = commentService.show();
        Result<List<Comment>> success = Result.success(list);
        System.out.println(success.getData());
        System.out.println(111);
        return success;
    }

    @GetMapping(value = "/{id}")
    public Result<Comment> getById(@PathVariable("id") String id) {
        return Result.success(commentService.getById(id));
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
