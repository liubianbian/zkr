package com.test.demo.configuration;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * feign统一拦截器
 * 对traceId进行封装处理
 * 将traceId塞到请求头部中
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = String.valueOf(new Random(10).nextInt(100));
        requestTemplate.header("LOG_TRACE_ID", traceId);
    }
}
