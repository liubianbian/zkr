package com.test.gateway.filter.abstracts;

import com.test.gateway.util.WebUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 响应数据日志采集抽象过滤器
 */
public abstract class AbstractResponseLogGlobalFilter implements GlobalFilter, Ordered {
    
    /**
     * 过滤器执行顺序，越小优先级越高
     */
    @Override
    public int getOrder() {
    	return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 11;
    }
    
    /**
     * 过滤器逻辑执行方法
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	try{
            return processPre(exchange).then(chain.filter(exchange)).then(Mono.fromRunnable(() -> {processPost(exchange);}));
    	}catch(Exception e){
			return WebUtil.writeResponse(exchange, "响应数据日志采集处理过程出现错误");
    	}
    }
    
    /**
     * 处理响应数据日志采集Pre逻辑
     * @param exchange 请求上下文
     * @return Mono<Void>对象<br>
     */
    protected abstract Mono<Void> processPre(ServerWebExchange exchange) throws Exception;

    /**
     * 处理响应数据日志采集Post逻辑
     * @param exchange 请求上下文
     * @return Mono<Void>对象<br>
     */
    protected abstract void processPost(ServerWebExchange exchange);
    
}
