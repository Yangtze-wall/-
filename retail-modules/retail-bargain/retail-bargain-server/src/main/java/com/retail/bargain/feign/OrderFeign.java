package com.retail.bargain.feign;

import com.retail.bargain.vo.OrderVo;
import com.retail.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单服务远程调用
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

    /**
     * 订单号查询订单信息
     */
    @PostMapping("findByOrderSn/{orderSn}")
    public Result<OrderVo> orderFindByOrderSn(@PathVariable("orderSn") String orderSn);

    /**
     * 订单状态修改  (支付成功后)
     */
    @PostMapping("updateOrderStatus/{orderSn}")
    public Result updateOrderStatus(@PathVariable("orderSn") String orderSn);
}
