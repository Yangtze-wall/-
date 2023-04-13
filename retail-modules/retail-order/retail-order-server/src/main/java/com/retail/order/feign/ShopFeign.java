package com.retail.order.feign;

import com.retail.common.domain.vo.InventoryVo;
import com.retail.common.domain.vo.SpuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("retail-shop")
public interface ShopFeign {

    @PostMapping("/shop/inventory/selectInventory")
   public InventoryVo selectInventory(@RequestParam("spuId") Long spuId);

    @PostMapping("/shop/spu/selectSpu")
   public SpuVo selectSpu(@RequestParam("spuId") Long spuId);

}
