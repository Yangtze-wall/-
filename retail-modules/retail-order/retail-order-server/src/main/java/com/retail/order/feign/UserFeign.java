package com.retail.order.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "retail-user",path = "user/useraddress")
public interface UserFeign {

//    @PostMapping("selectOrderAddress/{id}")
//    public List<AddressVo> selectOrderAddress(@PathVariable("id") Long id);
}
