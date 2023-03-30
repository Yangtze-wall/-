package com.retail.bargain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.bargain.domain.UrlEntry;
import com.retail.common.result.Result;

import java.util.List;

/**
 * @author UrlService
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.service
 * @date: 2023-03-27  20:07
 * @Created by:  12871
 * @Description:
 * @Version:
 */
public interface UrlService extends IService<UrlEntry> {
    Result<List<UrlEntry>> getUrlList();

}
