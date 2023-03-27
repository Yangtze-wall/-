package com.retail.colonel.fegin;


import com.retail.common.domain.vo.CommissionVo;
import com.retail.common.domain.vo.UserEntityVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "retail-user",path = "user/userrecord")
public interface UserFeign {


    @RequestMapping("/selectCommission")
    public List<CommissionVo> selectCommission(@RequestBody UserEntityVo userEntityVo);
}
