package com.retail.bargain.feign;

import com.retail.common.domain.vo.SkuEntityVo;
import com.retail.common.domain.vo.InventoryEntityVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("shop/inventory/findByInventoryEntity/{spuId}")
    Result<InventoryEntityVo> findByInventoryEntity(@PathVariable("spuId") Long spuId);

    @PostMapping("shop/sku/findBySkuEntry/{spuId}")
    public Result<SkuEntityVo> findBySkuEntry(@PathVariable("spuId") Long spuId);

    @PostMapping("shop/sku/updateSkuSell")
    public Result updateSkuSell(@RequestBody SkuEntityVo skuEntityVo);

    @PutMapping("shop/inventory/updateInventoryLock")
    public Result updateInventoryLock(@RequestBody InventoryEntityVo inventoryEntityVo);


}
