package com.retail.colonel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.retail.colonel.domain.SeckillEntity;
import com.retail.common.domain.vo.SeckillSpuVo;
import com.retail.common.result.Result;

public interface SeckillService extends IService<SeckillEntity> {


    Result<SeckillSpuVo> itemDetail(Long id);

}
