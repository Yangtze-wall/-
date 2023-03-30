package com.retail.user.controller;

import com.retail.user.service.PowerSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PowerController
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.user.controller
 * @date: 2023-03-26  19:40
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@RequestMapping("user/power")
@RestController
public class PowerController {
    @Autowired
    private PowerSerivce powerSerivce;

}
