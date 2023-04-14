package com.retail.order.feign;

import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("user/useraddress/findUserAddressById/{id}")
    public Result<UserAddressEntityVo> findUserAddressById(@PathVariable("id")Long id);

    @PostMapping("user/user/findUserById/{id}")
    public Result<UserEntityVo> findUserById(@PathVariable("id")Long id);

    @PostMapping("user/integrationhistory/insertIntegrationByOrder")
    public Result insertIntegrationByOrder(@RequestBody IntegrationHistoryEntityVo integrationHistoryEntityVo);

    @PostMapping("user/user/updateUser")
    public Result updateUser(@RequestBody UserEntityVo userEntityVo);

    @PostMapping("user/userrecord/insertRecord")
    public Result insertRecord(@RequestBody UserRecordEntityVo userRecordEntityVo);
}
