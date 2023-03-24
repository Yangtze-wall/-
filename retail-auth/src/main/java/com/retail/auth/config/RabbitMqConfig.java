package com.retail.auth.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.config
 * @Classname: RabbitMqConfig
 * @CreateTime: 2023-03-24  19:30
 * @Created by: 喵喵
 * @Description:
 * @Version:
 */
@Configuration
public class RabbitMqConfig {

    //创建队列
    @Bean
    public Queue createRetailSmsQueue(){
        return new Queue("retail.queue.sms");
    }
    //创建点对点交换机
    @Bean
    public Exchange createRetailExchange(){
        return new DirectExchange("retail.exchange.sms");
    }

    //绑定
    @Bean
    public Binding createBinding(){
        return new Binding("retail.queue.sms", Binding.DestinationType.QUEUE,
                "retail.exchange.sms","retail.queue.sms",null);
    }

}
