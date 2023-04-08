package com.retail.user.controller;

import java.util.List;


import com.retail.common.result.Result;
import com.retail.common.domain.vo.UserAddressEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 地址下拉框
     * @return
     */
    @PostMapping("/selectUserAddressByUserId")
    public Result<List<UserAddressEntityVo>> selectStoreCouponByUserId(){
        List<UserAddressEntityVo> userAddressEntityListvo=userAddressService.selectStoreCouponByUserId();
        return Result.success(userAddressEntityListvo);
    }

    /**
     * 根据 id 查找用户地址信息
     * @param id
     * @return
     */
    @PostMapping("/findUserAddressById/{id}")
    public Result<UserAddressEntityVo>  findUserAddressById(@PathVariable("id")Long id){
        UserAddressEntityVo userAddressEntityVo=userAddressService.findUserAddressById(id);
        return Result.success(userAddressEntityVo);
    }




}
