package com.retail.colonel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.colonel.domain.GroupConfig;
import com.retail.colonel.mapper.GroupConfigMapper;
import com.retail.colonel.service.GroupConfigService;
import org.springframework.stereotype.Service;


@Service("groupConfigService")
public class GroupConfigServiceImpl extends ServiceImpl<GroupConfigMapper, GroupConfig> implements GroupConfigService {



}
