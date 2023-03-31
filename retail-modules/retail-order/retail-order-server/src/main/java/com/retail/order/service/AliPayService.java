package com.retail.order.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AliPayService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.order.service
 * @date: 2023-03-30  20:21
 * @Created by:  12871
 * @Description:
 * @Version:
 */
public interface AliPayService {

    void aliPay(HttpServletResponse response, HttpServletRequest request, String total_amount);
}
