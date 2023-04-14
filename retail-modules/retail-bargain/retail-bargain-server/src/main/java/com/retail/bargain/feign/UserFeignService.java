package com.retail.bargain.feign;

import com.retail.common.domain.vo.IntegrationHistoryEntityVo;
import com.retail.common.domain.vo.UserAddressEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author UserFeignService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.feign
 * @date: 2023-03-29  14:45
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@FeignClient("retail-user")
public interface UserFeignService {
    @PostMapping("/findByUserIdList")
    public Result<List<UserAddressEntityVo>> findByUserIdList(@RequestParam("userId") Long userId);

    @PostMapping("user/integrationhistory/integrationHistoryInsert")
    public Result integrationHistoryInsert(@RequestBody IntegrationHistoryEntityVo integrationHistoryEntityVo);
}
