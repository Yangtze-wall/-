package com.retail.colonel.remote;

import com.retail.colonel.domain.ColonelEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName ColonelFeign
 * Date 2023/4/3 21:32
 **/
@FeignClient(name = "retail-colonel",path = "colonel")
public interface ColonelFeign {
    @GetMapping("/colonel/findById/{id}")
    ColonelEntity findById(@PathVariable("id") Long id);
}
