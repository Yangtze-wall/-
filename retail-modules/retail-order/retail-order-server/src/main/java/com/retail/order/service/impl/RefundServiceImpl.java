package com.retail.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.retail.order.domain.RefundEntity;
import com.retail.order.mapper.RefundMapper;
import com.retail.order.service.RefundService;
import org.springframework.stereotype.Service;


@Service("refundInfoService")
public class RefundServiceImpl extends ServiceImpl<RefundMapper, RefundEntity> implements RefundService {


}
