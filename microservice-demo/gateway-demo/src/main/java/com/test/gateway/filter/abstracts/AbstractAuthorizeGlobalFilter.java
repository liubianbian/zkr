package com.test.gateway.filter.abstracts;


import com.test.gateway.util.WebUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 安全认证抽象过滤器
 */
public abstract class AbstractAuthorizeGlobalFilter implements GlobalFilter, Ordered {
    
    /**
     * 过滤器执行顺序，越小优先级越高
     */
    @Override
    public int getOrder() {
    	return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 7;
    }
    
    /**
     * 过滤器逻辑执行方法
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	try{
            ServerWebExchange serverWebExchange = process(exchange);
            return chain.filter(serverWebExchange);
    	}catch(Exception e){
			return WebUtil.writeResponse(exchange, "安全认证异常");
    	}
    }

    /**
     * 处理安全认证逻辑
     */
    protected abstract ServerWebExchange process(ServerWebExchange exchange) throws Exception;

}
