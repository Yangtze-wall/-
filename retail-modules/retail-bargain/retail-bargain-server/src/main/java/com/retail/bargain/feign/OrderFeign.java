package com.retail.bargain.feign;

import com.retail.bargain.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Lenovo
 * @Package_name com.retail.bargain.feign
 * @Description TODO
 * @createTime 2023/4/8 11:19
 */
@FeignClient(value = "retail-order",path = "order/order")
public interface OrderFeign {
    /**
     * 订单添加
     * @param orderEntity
     * @return
     */
    @PostMapping("/add")
    public Long addOrder(@RequestBody OrderVo orderEntity);
}
