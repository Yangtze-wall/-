package com.retail.order.feign;

import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.domain.vo.StoreCouponEntityVo;
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

    @PostMapping("shop/sku/findBySkuEntry/{spuId}")
    public Result<SkuEntityVo> findBySkuEntry(@PathVariable("spuId") Long spuId);

    @PostMapping("shop/storecoupon/findByIdStoreCoupon")
    public Result<StoreCouponEntityVo> findByIdStoreCoupon(@RequestParam("id") Long id);
}
