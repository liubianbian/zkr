package com.test.gateway.filter.defaults;

import com.test.gateway.bean.ResponseBean;
import com.test.gateway.filter.abstracts.AbstractBlanklistGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * IP黑名单实现过滤器
 */
@Slf4j
@Component
public class BlanklistGlobalFilter extends AbstractBlanklistGlobalFilter {

    @Override
    protected ServerWebExchange process(ServerWebExchange exchange) {
        log.info("请求进入IP黑名单过滤器");
        return exchange;
    }
    
}