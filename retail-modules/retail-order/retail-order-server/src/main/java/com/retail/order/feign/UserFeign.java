package com.retail.order.feign;

import com.retail.common.domain.vo.AddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "retail-user",path = "user/useraddress")
public interface UserFeign {

    @PostMapping("selectOrderAddress/{id}")
    public List<AddressVo> selectOrderAddress(@PathVariable("id") Long id);
}
