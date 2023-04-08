package com.retail.order.feign;

import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author UserFeignService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.feign
 * @date: 2023-03-29  21:07
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@FeignClient("retail-user")
public interface UserFeignService {
    @PostMapping("user/useraddress/findByIdAddress")
    public Result<UserAddressEntityVo> findByIdAddress(@RequestParam("id") Long id);

    @PostMapping("user/user/updateIntegration")
    Result updateIntegration(@RequestBody UserEntityVo userEntityVo);
}
