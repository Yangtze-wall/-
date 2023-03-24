package com.retail.colonel.service.impl;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.retail.colonel.fegin.ColonelApplyFeign;
import com.retail.common.domain.vo.UserEntityVo;
import com.retail.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.retail.colonel.mapper.ColonelApplyMapper;
import com.retail.colonel.domain.ColonelApplyEntity;
import com.retail.colonel.service.ColonelApplyService;


@Service("colonelApplyService")
public class ColonelApplyServiceImpl extends ServiceImpl<ColonelApplyMapper, ColonelApplyEntity> implements ColonelApplyService {


    @Autowired
    private ColonelApplyFeign colonelApplyFeign;

    @Override
    public Result colonelApplyService(ColonelApplyEntity colonelApplyEntity) {


       Result<UserEntityVo> userEntityVo = colonelApplyFeign.userInfo();
        UserEntityVo entityVo = userEntityVo.getData();
        colonelApplyEntity.setUserId(entityVo.getId());
        colonelApplyEntity.setStatus(1);
        colonelApplyEntity.setCreateTime(new Date());

        this.baseMapper.insert(colonelApplyEntity);



        return Result.success("团长申请成功");
    }
}
