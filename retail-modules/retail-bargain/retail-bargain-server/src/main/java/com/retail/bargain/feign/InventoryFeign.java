package com.retail.bargain.feign;

import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存服务远程调用
 * @author Lenovo
 * @Package_name com.retail.bargain.feign
 * @Description TODO
 * @createTime 2023/4/12 15:29
 */
@FeignClient(value = "retail-shop",path = "shop/inventory")
public interface InventoryFeign {
    /**
     * 库存表详情
     * @param spuId
     * @return
     */
    @PostMapping("/selectInventory")
    public InventoryVo selectInventory(@RequestParam("spuId") Long spuId);

    /**
     * 库存修改
     * @param inventoryVo
     * @return
     */
    @PostMapping("/updateInventory")
    public Result updateInventory(@RequestBody InventoryVo inventoryVo);

}
