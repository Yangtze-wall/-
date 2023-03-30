package com.retail.bargain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.bargain.domain.UrlEntry;
import com.retail.bargain.mapper.UrlMapper;
import com.retail.bargain.service.UrlService;
import com.retail.common.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author UrlServiceImpl
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.bargain.service.impl
 * @date: 2023-03-27  20:08
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Service("urlService")
public class UrlServiceImpl extends ServiceImpl<UrlMapper, UrlEntry> implements UrlService {
    @Override
    public Result<List<UrlEntry>> getUrlList() {
        List<UrlEntry> urlEntryList =
                baseMapper.selectList(new QueryWrapper<UrlEntry>()
                        .select(UrlEntry.class,item ->  !item.getColumn().equals("url_key"))
                        .lambda().eq(UrlEntry::getUrlStatus,0));
        return Result.success(urlEntryList);
    }
}
