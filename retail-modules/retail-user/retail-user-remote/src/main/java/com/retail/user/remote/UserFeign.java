package com.retail.user.remote;

import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient (contextId = "userFeign",value = "retail-user")

public interface UserFeign {
    @GetMapping("/user/user/colonelLogin/{phone}")
    Result<UserEntityVo> loginPasswordColonel(@PathVariable("phone") String phone);
}
