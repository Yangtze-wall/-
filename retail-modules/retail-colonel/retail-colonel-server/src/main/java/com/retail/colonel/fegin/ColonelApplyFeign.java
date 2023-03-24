package com.retail.colonel.fegin;

import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ProjectName:    retail-cloud-3.23
 * @Package:        com.retail.colonel.fegin
 * @ClassName:      ColonelApplyFeign
 * @Author:     2766395184
 * @Description:
 * @Date:    2023/3/23 20:03
 * @Version:    1.0
 */
@FeignClient(value = "retail-user",path = "user/user")
public interface ColonelApplyFeign {

    @PostMapping("userInfo")
    public Result<UserEntityVo> userInfo();
}
