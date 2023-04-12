package com.retail.user.controller;

import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserRecordEntity;
import com.retail.user.mapper.UserRecordMapper;
import com.retail.user.service.UserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


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
    @Resource
    public UserRecordMapper recordMapper;

    @RequestMapping("/selectCommission")
    public List<CommissionVo> selectCommission(@RequestBody UserEntityVo userEntityVo){

        if (userEntityVo==null){
            log.error("佣金流水 参数为空");
            return null;
        }
        List<CommissionVo> commissionVoList=  userRecordService.selectCommission(userEntityVo);

        return commissionVoList;
    }

    /**
     * 账户记录流水添加
     */
    @PostMapping("recordAdd")
    public Result recordAdd(@RequestBody UserRecordEntity userRecordEntity){
        int insert = recordMapper.insert(userRecordEntity);
        if (insert<0){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }



}
