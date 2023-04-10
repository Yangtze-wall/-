package com.retail.order.service.impl;

import com.retail.order.domain.RetailOrderInfoEntity;
import com.retail.order.mapper.ReatilOrderInfoMapper;
import com.retail.order.service.ReatilOrderInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;





@Service("reatilOrderInfoService")
public class ReatilOrderInfoServiceImpl extends ServiceImpl<ReatilOrderInfoMapper, RetailOrderInfoEntity> implements ReatilOrderInfoService {



}
