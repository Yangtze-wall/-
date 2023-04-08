package com.retail.order.feign;

import com.retail.common.domain.vo.ProductVo;
import com.retail.common.domain.vo.StoreCouponEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.feign
 * @Classname: ShopFeignService
 * @CreateTime: 2023-04-07  19:08
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@FeignClient("retail-shop")
public interface ShopFeignService {
    @PostMapping("shop/sku/findBySkuId/{id}")
    public Result<ProductVo> findBySkuId(@PathVariable("id")Long id);

    @PostMapping("shop/storecoupon/findStoreCouponById/{id}")
    public Result<StoreCouponEntityVo> findStoreCouponById(@PathVariable("id")Long id);
}
