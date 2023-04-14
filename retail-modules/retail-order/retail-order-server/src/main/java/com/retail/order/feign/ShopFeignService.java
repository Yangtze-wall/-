package com.retail.order.feign;

import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.domain.vo.StoreCouponEntityVo;
import com.retail.common.domain.vo.UserRecordEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author ShopFeignService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.feign
 * @date: 2023-03-27  14:40
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@FeignClient("retail-shop")
public interface ShopFeignService {

    @GetMapping("shop/sku/findBySkuEntry/{spuId}")
    public Result<SkuEntityVo> findBySkuEntry(@PathVariable("spuId") Long spuId);

    @GetMapping("shop/storecoupon/findByIdStoreCoupon/{id}")
    public Result<StoreCouponEntityVo> findByIdStoreCoupon(@PathVariable("id") Long id);


//    @PostMapping("shop/retaiusercouponcenter/isDelRetaiUserCoupon")
    @PostMapping("/retaiusercouponcenter/isDelRetaiUserCoupon")
    Result isDelRetaiUserCoupon(@RequestParam("couponId") Long couponId);

    @PutMapping("shop/inventory/updateInventoryLock")
    public Result updateInventoryLock(@RequestBody InventoryEntityVo inventoryEntityVo);

    @GetMapping("shop/inventory/findByInventoryEntity/{spuId}")
    Result<InventoryEntityVo> findByInventoryEntity(@PathVariable("spuId") Long spuId);


}
