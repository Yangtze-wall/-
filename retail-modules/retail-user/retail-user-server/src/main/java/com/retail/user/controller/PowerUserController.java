package com.retail.user.controller;

import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.retail.user.domain.request.UserEntryPowerRequest;
import com.retail.user.domain.vo.UserEntityPowerListVo;
import com.retail.user.domain.vo.UserEntryPowerVo;
import com.retail.common.result.PageResult;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retail.user.service.PowerUserService;


/**
 * 权限  用户 中间表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-23 19:31:59
 */
@RestController
@RequestMapping("user/poweruser")
public class PowerUserController {

    @Autowired
    private PowerUserService powerUserService;

    /**
     * 权限列表
     * @param userEntryPowerRequest
     * @return
     */
    @PostMapping("/getUserPowerList")
    public Result<PageResult<UserEntryPowerVo>> getPowerUserEntryList(@RequestBody UserEntryPowerRequest userEntryPowerRequest){
        PageHelper.startPage(userEntryPowerRequest.getPageNum(),userEntryPowerRequest.getPageSize());
        List<UserEntryPowerVo> powerUserEntryList = powerUserService.getPowerUserEntryList();
        PageInfo<UserEntryPowerVo> userEntryPowerVoPageInfo = new PageInfo<>(powerUserEntryList);
        long total = userEntryPowerVoPageInfo.getTotal();
        Result<PageResult<UserEntryPowerVo>> pageResultResult = PageResult.toResult(total, powerUserEntryList);
        return pageResultResult;
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("/findByIdUserPower/{id}")
    public Result<UserEntityPowerListVo> findByIdUserPower(@PathVariable("id") Long id){
        UserEntityPowerListVo userEntityPowerListVo =powerUserService.findByIdUserPower(id);
        return Result.success(userEntityPowerListVo);
    }

    /**
     * 添加
     */
    @PostMapping("/insertUserPower")
    public  Result insertUserPower(@RequestBody UserEntityPowerListVo userEntityPowerListVo){
        return powerUserService.insertUserPower(userEntityPowerListVo);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delUserPower/{id}")
    public Result delUserPower(@PathVariable("id") Long id){
        return powerUserService.delUserPower(id);
    }


}
