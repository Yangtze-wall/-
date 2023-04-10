package com.retail.bargain.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 秒杀商品标题
     */
    private String seckillName;

    /**
     * 分页
     */
    private Integer pageNum=1;
    private Integer pageSize=3;
}
