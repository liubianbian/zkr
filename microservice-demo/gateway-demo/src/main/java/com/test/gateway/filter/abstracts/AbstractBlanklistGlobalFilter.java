package com.test.gateway.filter.abstracts;


import com.test.gateway.util.WebUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * IP黑名单抽象过滤器
 */
public abstract class AbstractBlanklistGlobalFilter implements GlobalFilter, Ordered {
    
    /**
     * 过滤器执行顺序，越小优先级越高
     */
    @Override
    public int getOrder() {
    	return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 8;
    }
    
    /**
     * 过滤器逻辑执行方法
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	try{
            ServerWebExchange newServerWebExchange = process(exchange);
            return chain.filter(newServerWebExchange);
    	}catch(Exception e){
			return WebUtil.writeResponse(exchange, "过滤器异常");
    	}
    }

    /**
     * 处理IP黑名单逻辑
     */
    protected abstract ServerWebExchange process(ServerWebExchange exchange);

}
