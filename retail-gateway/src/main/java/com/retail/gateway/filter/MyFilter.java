package com.retail.gateway.filter;

import com.retail.common.constant.TokenConstants;
import com.retail.common.utils.JwtUtils;
import com.retail.common.utils.StringUtils;
import com.retail.gateway.utils.GatewayUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MyFilter
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.gateway.filter
 * @date: 2023-03-24  09:40
 * @Created by:  12871
 * @Description:
 * @Version:
 */
@Configuration
@Log4j2
public class MyFilter implements GlobalFilter, Ordered {
    List<String> PATHS=new ArrayList<String>(){{
        add("/auth/**");
        add("/shop/**");
        add("/order/payment/payed/notify");
    }};
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (StringUtils.matches(path,PATHS)){
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)){
            return GatewayUtils.errorResponse(exchange,"token不存在");
        }
        try {
            JwtUtils.parseToken(token);
        }catch (Exception e){
            return GatewayUtils.errorResponse(exchange,"token不合法");
        }
        String userKey = JwtUtils.getUserKey(token);
        if (!redisTemplate.hasKey(TokenConstants.LOGIN_TOKEN_KEY+userKey)){
            return GatewayUtils.errorResponse(exchange,"token过期");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
