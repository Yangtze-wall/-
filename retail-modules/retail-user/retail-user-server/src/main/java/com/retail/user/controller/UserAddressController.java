package com.retail.user.controller;

import java.util.List;


import com.retail.common.domain.vo.AddressVo;
import com.retail.common.result.Result;
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

    /**
     * 根据用户id查地址
     * @param id
     * @return
     */
    @PostMapping("selectOrderAddress/{id}")
    public List<AddressVo> selectOrderAddress(@PathVariable("id") Long id){
        List<AddressVo> addressVoList=  userAddressService.selectOrderAddress(id);

        return addressVoList;
    }
    @PostMapping("selectOrderAddressTwo")
    public Result<List<AddressVo>> selectOrderAddressTwo(){
        List<AddressVo> addressVoList=  userAddressService.selectOrderAddressTwo();
        return Result.success(addressVoList);
    }
}
