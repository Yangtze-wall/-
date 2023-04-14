package com.retail.shop.job;

import com.alibaba.fastjson.JSON;
import com.retail.common.domain.vo.ProductVo;
import com.retail.shop.service.SkuService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.shop.config
 * @Classname: EsSyn
 * @CreateTime: 2023-03-31  16:53
 * @Created by: 喵喵
 * @Description: 同步
 * @Version:
 */
@Component
@Log4j2
public class EsSyn {
    @Autowired
    private SkuService skuService;

    RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("139.196.95.132", 9200, "http")));

    private static String INDEX_NAME="product";
    //    使用定时器进行表数据到ES库的增量同步
//    @Scheduled(cron = "0/50 * * * * *")


    @XxlJob("bbbb")
    public void insert(){
        List<ProductVo> list= skuService.findSkuList();
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        //循环添加
        list.stream().forEach(c->{
            if (c.getSkuId().intValue()%shardTotal==shardIndex){
                System.out.println(c);
                log.info(c);
            }
            try {
                //添加
                IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
                indexRequest.id(c.getSkuId().toString());
                indexRequest.source(JSON.toJSONString(c), XContentType.JSON);
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
