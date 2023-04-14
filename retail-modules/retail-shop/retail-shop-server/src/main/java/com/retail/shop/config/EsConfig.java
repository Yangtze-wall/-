package com.retail.shop.config;

import com.alibaba.fastjson.JSON;

import com.retail.common.domain.vo.ProductVo;
import com.retail.shop.service.SkuService;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: yuekao0317
 * @BelongsPackage: com.bawei.config
 * @Classname: EsConfig
 * @CreateTime: 2023-03-17  11:49
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Component
@Log4j2
public class EsConfig implements ApplicationRunner{


    @Autowired
    private SkuService skuService;

    RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("139.196.95.132", 9200, "http")));

    private static String INDEX_NAME="product";
    @Override
//    @XxlJob(value = "aaaa")
    public void run(ApplicationArguments args) throws Exception {

       List<ProductVo> list= skuService.findSkuList();

        //循环添加
        list.stream().forEach(c->{
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
