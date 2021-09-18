package com.test.gateway.filter.defaults;

import com.test.gateway.filter.abstracts.AbstractAuthorizeGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * JWT鉴权认证实现过滤器
 */
@Slf4j
@Component
public class JwtGlobalFilter extends AbstractAuthorizeGlobalFilter {

	@Override
	protected ServerWebExchange process(ServerWebExchange exchange) throws Exception {
		log.info("请求进入鉴权认证过滤器");
		return exchange;
	}
	
}
