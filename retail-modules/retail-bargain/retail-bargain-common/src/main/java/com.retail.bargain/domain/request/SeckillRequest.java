package com.retail.bargain.domain.request;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀页面搜索条件
 * @author Lenovo
 * @Package_name com.retail.bargain.domain.request
 * @Description TODO
 * @createTime 2023/3/24 17:25
 */
@Data
public class SeckillRequest {
    /**
     * 时间区间
     */
    private Date time;

    /**
     * 分页
     */
    private Integer pageNum;
    private Integer pageSize;
}
