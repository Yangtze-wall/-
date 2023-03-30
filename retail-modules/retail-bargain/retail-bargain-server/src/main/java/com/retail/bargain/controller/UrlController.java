package com.retail.bargain.controller;

import com.retail.bargain.domain.UrlEntry;
import com.retail.bargain.service.UrlService;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author UrlController
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.controller
 * @date: 2023-03-27  20:06
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("bargain/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/getUrlList")
    public Result<List<UrlEntry>> getUrlList(){
        return urlService.getUrlList();
    }

}
