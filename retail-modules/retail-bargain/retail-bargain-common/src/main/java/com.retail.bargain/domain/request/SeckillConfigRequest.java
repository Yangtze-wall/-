package com.retail.bargain.domain.request;

import lombok.Data;

/**
 * 秒杀配置搜索条件
 * @author Lenovo
 * @Package_name com.retail.bargain.domain.request
 * @Description TODO
 * @createTime 2023/3/25 11:43
 */
@Data
public class SeckillConfigRequest {
    /**
     * 秒杀配置名称
     */
    private String seckillConfigName;
    /**
     * 状态
     */
    private Integer status;


    /**
     * 分页
     */

    private Integer pageNum;
    private Integer pageSize;
}
