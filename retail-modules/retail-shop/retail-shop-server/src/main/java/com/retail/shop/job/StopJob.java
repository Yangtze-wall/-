package com.retail.shop.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * ClassName StopJob
 * Date 2023/3/28 19:01
 **/
@Component
public class StopJob {
    @XxlJob("test")
    public void ss(){
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        System.out.println("当前是第"+shardIndex);
        System.out.println("总共有"+shardTotal);
        System.out.println("这个方案");
    }
}




















