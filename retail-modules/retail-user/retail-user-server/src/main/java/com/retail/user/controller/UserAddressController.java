package com.retail.user.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.user.domain.UserAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.retail.user.service.UserAddressService;


/**
 * 收货地址表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:26:56
 */

@RestController
@RequestMapping("user/useraddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping(value = "/list/{id}")
    public Result<List<UserAddressEntity>> list(@PathVariable("id") Long id) {
        List<UserAddressEntity> list = userAddressService.selectUserAddressEntity(id);
        return Result.success(list);
    }

    @GetMapping(value = "/{id}")
    public Result getById(@PathVariable("id") Long id) {
        UserAddressEntity userAddressEntity = userAddressService.findById(id);
        return Result.success(userAddressEntity);
    }

    @PostMapping(value = "/create")
    public Result create(@RequestBody UserAddressEntity params) {
        userAddressService.insert(params);
        return Result.success("成功");
    }

    @PostMapping(value = "/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        userAddressService.delete(id);
        return Result.success(null,"成功");
    }

    @PostMapping(value = "/update")
    public Result delete(@RequestBody UserAddressEntity params) {
        userAddressService.updateAddress(params);
        return Result.success(null,"成功");
    }
    @GetMapping("/updateStatus/{id}/{userId}")
    public Result updateStatus(@PathVariable("id") Long id,@PathVariable("userId") Long userId){
        userAddressService.upateStatus(id,userId);
        return Result.success(null,"成功");
    }
}
