package com.retail.user.controller;

import java.util.List;


import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.user.service.UserRecordService;


/**
 * 账户记录流水表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 09:50:51
 */
@RestController
@RequestMapping("user/userrecord")
public class UserRecordController {

    @Autowired
    private UserRecordService userRecordService;

    /**
     * 订单支付成功 添加账户记录流水表
     * @param userRecordEntityVo
     * @return
     */
    @PostMapping("/insertRecord")
    public Result insertRecord(@RequestBody UserRecordEntityVo userRecordEntityVo){
        return userRecordService.insertRecord(userRecordEntityVo);
    }
}
