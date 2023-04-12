package com.retail.bargain.feign;

import com.retail.bargain.vo.UserIntegralAdd;
import com.retail.bargain.vo.UserRecordEntity;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务远程调用
 * @author Lenovo
 * @Package_name com.retail.bargain.feign
 * @Description TODO
 * @createTime 2023/4/11 18:57
 */
@FeignClient(value = "retail-user",url = "127.0.0.1:9203")
public interface UserFeign {
    /**
     * 积分增加
     * @param userIntegralAdd
     * @return
     */
    @PostMapping("user/integrationhistory/add")
    public Result add(@RequestBody UserIntegralAdd userIntegralAdd);
    /**
     * 账户记录流水添加
     */
    @PostMapping("user/userrecord/recordAdd")
    public Result recordAdd(@RequestBody UserRecordEntity userRecordEntity);
}
