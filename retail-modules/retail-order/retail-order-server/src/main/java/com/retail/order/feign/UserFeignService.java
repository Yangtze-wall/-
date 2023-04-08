package com.retail.order.feign;

import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.feign
 * @Classname: UserFeignService
 * @CreateTime: 2023-04-07  18:32
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@FeignClient("retail-user")
public interface UserFeignService {

    @PostMapping("user/user/useraddress/findUserAddressById/{id}")
    public Result<UserAddressEntityVo> findUserAddressById(@PathVariable("id")Long id);
}
