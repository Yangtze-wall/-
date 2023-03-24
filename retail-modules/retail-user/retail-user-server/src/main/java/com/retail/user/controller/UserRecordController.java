package com.retail.user.controller;

import java.util.List;


import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserRecordController {

    @Autowired
    private UserRecordService userRecordService;

    @RequestMapping("/selectCommission")
    public List<CommissionVo> selectCommission(@RequestBody UserEntityVo userEntityVo){

        if (userEntityVo==null){
            log.error("佣金流水 参数为空");
            return null;
        }
        List<CommissionVo> commissionVoList=  userRecordService.selectCommission(userEntityVo);

        return commissionVoList;
    }




}
