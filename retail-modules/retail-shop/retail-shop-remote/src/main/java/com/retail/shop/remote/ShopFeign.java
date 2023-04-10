package com.retail.shop.remote;

import com.retail.shop.domain.SkuEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName ShopFeign
 * Date 2023/3/29 16:02
 **/
@FeignClient(name = "retail-shop")
public interface ShopFeign {
    @GetMapping("sku/info/{id}")
    SkuEntity getInfo(@PathVariable("id") Long id);
}
