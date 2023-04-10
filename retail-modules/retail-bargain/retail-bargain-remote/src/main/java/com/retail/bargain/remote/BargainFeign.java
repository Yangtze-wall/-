package com.retail.bargain.remote;

import com.retail.bargain.domain.SeckillEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "retail-bargain",path = "colonel/seckill")
public interface BargainFeign {
@GetMapping("show/{id}")
    SeckillEntity show(@PathVariable("id") Long id);
}
