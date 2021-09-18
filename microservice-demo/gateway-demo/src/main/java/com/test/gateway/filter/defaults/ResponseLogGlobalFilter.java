package com.test.gateway.filter.defaults;

import com.test.gateway.constant.ConstantEnum;
import com.test.gateway.filter.abstracts.AbstractResponseLogGlobalFilter;
import com.test.gateway.util.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Random;

/**
 * 响应数据日志采集实现过滤器
 */
@Slf4j
@Component
public class ResponseLogGlobalFilter extends AbstractResponseLogGlobalFilter {
	
	@Override
	protected Mono<Void> processPre(ServerWebExchange exchange) throws Exception {
		exchange.getAttributes().put(ConstantEnum.SYSTEM_START_TIME.value(), System.currentTimeMillis());
        exchange.getAttributes().put(ConstantEnum.LOG_TRACE_ID.value(), new Random(10));
        log.info("日志采集前置过滤器调用：{}");
        return Mono.empty();
	}
	
	@Override
	protected void processPost(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		LinkedHashMap<String, String> logMap = new LinkedHashMap<String, String>(16);
        logMap.put(ConstantEnum.LOG_TYPE.value(), getClass().getSimpleName());
        logMap.put(ConstantEnum.LOG_TRACE_ID.value(), exchange.getAttribute(ConstantEnum.LOG_TRACE_ID.value()));
        logMap.put(ConstantEnum.LOG_REQ_METHOD.value(), request.getMethodValue());
        logMap.put(ConstantEnum.LOG_CONTENT_TYPE.value(), exchange.getResponse().getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        logMap.put(ConstantEnum.LOG_REQ_USERID.value(), exchange.getAttribute(ConstantEnum.LOG_REQ_USERID.value()));
        String responseBody = exchange.getAttribute(ConstantEnum.RESPONSE_BODY_CONTENT.value());
        logMap.put(ConstantEnum.LOG_RES_DATA.value(), responseBody);
    	Long startTime = exchange.getAttribute(ConstantEnum.SYSTEM_START_TIME.value());
    	logMap.put(ConstantEnum.LOG_EXPEND_TIME.value(),(System.currentTimeMillis()-startTime)+"ms");
    	exchange.getAttributes().put(ConstantEnum.RECORDER_PRINT_LOG.value(), logMap);
    	log.info("日志采集后置处理器调用：{}", FastJsonUtil.toJson(logMap));
	}
	
}
