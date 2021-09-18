package com.test.gateway.filter.defaults;

import com.test.gateway.filter.abstracts.AbstractRequestLogGlobalFilter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



/**
 * 请求数据日志采集实现过滤器
 */
@Slf4j
@Component
public class RequestLogGlobalFilter extends AbstractRequestLogGlobalFilter {
	
	@Override
	protected Mono<Void> process(ServerWebExchange exchange) throws Exception {
        log.info("请求进入请求数据日志采集处理过滤器");
        return Mono.empty();
	}
	
}
