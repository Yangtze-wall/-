package com.retail.user.controller;

import java.util.List;


import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.result.Result;
import com.retail.user.domain.UserAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    //   /user/useraddress/findByUserIdList/
    @PostMapping("/findByUserIdList")
    public Result<List<UserAddressEntityVo>> findByUserIdList(){
      return userAddressService.findByUserIdList();
    }
    // user/useraddress/findByIdAddress
    @PostMapping("/findByIdAddress")
    public Result<UserAddressEntityVo> findByIdAddress(@RequestParam("id") Long id){
        return userAddressService.findByIdAddress(id);
    }
}
