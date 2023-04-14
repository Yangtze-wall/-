package com.retail.auth.feign;

import com.retail.common.domain.request.UserEntityRequest;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserLoginCodeVo;
import com.retail.common.domain.vo.UserLoginPasswordVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author UserFeignService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.feign
 * @date: 2023-03-23  17:22
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@FeignClient("retail-user")
public interface UserFeignService {
    @PostMapping("user/user/register")
    Result register(@RequestBody UserEntityRequest userEntityRequest);


    @PostMapping("user/user/loginPassword")
    Result<UserEntityVo> loginPassword(@RequestBody UserLoginPasswordVo userLoginPasswordVo);

    @PostMapping("user/user/loginPasswordColonel")
    Result<UserEntityVo> loginPasswordColonel(@RequestBody UserLoginPasswordVo userLoginPasswordVo);


    @PostMapping("user/user/loginCode")
     Result<UserEntityVo> loginCode(@RequestBody UserLoginCodeVo userLoginCodeVo);

    @PostMapping("/user/user/colonelLogin/{phone}")
    Result<UserEntityVo> loginPasswordColonel(@PathVariable("phone") String phone);

}
