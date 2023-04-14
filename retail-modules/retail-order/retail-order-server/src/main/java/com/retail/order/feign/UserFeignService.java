package com.retail.order.feign;

import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("user/user/findByIdUser/{userId}")
    public Result<UserEntityVo> findByIdUser(@PathVariable("userId") Long userId);

    //  user/integrationhistory/integrationHistoryInsert
    @PostMapping("user/integrationhistory/integrationHistoryInsert")
    public Result integrationHistoryInsert(@RequestBody IntegrationHistoryEntityVo integrationHistoryEntityVo);

    @PostMapping("user/userrecord/userRecordInsert")
    Result userRecordInsert(@RequestBody UserRecordEntityVo userRecordEntityVo);
}
