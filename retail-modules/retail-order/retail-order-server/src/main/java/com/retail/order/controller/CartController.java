package com.retail.order.controller;


import com.retail.common.domain.vo.CartEntityVo;
import com.retail.common.domain.vo.OrderEntityVo;
import com.retail.common.result.Result;
import com.retail.order.domain.CartEntity;
import com.retail.order.domain.OrderEntity;
import com.retail.order.service.CartService;
import com.retail.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 购物车表
 *
 * @author fengge
 * @email 1287137373@qq.com
 * @date 2023-03-22 18:50:56
 */
@RestController
@RequestMapping("order/retailcart")
public class CartController {

    @Autowired
    private CartService cartService;



    /**
     * 商品加入购物车
     */
    @PostMapping("/insertCart")
    public Result insertCart(@RequestBody CartEntityVo cartEntityVo){
        return cartService.insertCart(cartEntityVo);
    }

    /**
     * 购物车列表
     * @return
     */
    @PostMapping("/getCartList")
    public Result<List<CartEntityVo>> getCartList(){
        return cartService.getCartList();
    }

    /**
     * 修改购买商品数量
     */
    @PostMapping("/updateCartNum")
    public Result updateCartNum(@RequestBody CartEntity cartEntity){
        return cartService.updateCartNum(cartEntity);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("/findCartByCartId/{id}")
    public Result<CartEntityVo> findCartByCartId(@PathVariable("id") Long id ){
        return cartService.findCartByCartId(id);

    }


}
