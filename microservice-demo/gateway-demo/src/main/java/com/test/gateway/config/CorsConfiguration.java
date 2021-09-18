//package com.test.gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.cors.reactive.CorsUtils;
//import org.springframework.web.server.WebFilter;
//
//import reactor.core.publisher.Mono;
//
///**
// * Cors跨域配置
// * @createdate 2018年11月5日 下午9:12:53
// */
//@Configuration
//public class CorsConfiguration {
//
//	   /**支持的请求头，如果有自定义的header字段请自己添加（不能使用*）**/
//    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client";
//
//    /**支持的请求方法，*表示支持所有的方法**/
//    private static final String ALLOWED_METHODS = "*";
//
//    /**支持的域名，*表示所有的域名**/
//    private static final String ALLOWED_ORIGIN = "*";
//
//    /**支持的响应头**/
//    private static final String ALLOWED_Expose = "*";
//
//    /**支持的最大有效时间，单位为秒**/
//    private static final String MAX_AGE = "18000L";
//
//    @Bean
//    public WebFilter corsFilter() {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            if(!CorsUtils.isCorsRequest(request)) {
//                return chain.filter(exchange);
//            }
//            ServerHttpResponse response = exchange.getResponse();
//            HttpHeaders headers = response.getHeaders();
//            headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
//            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
//            headers.add("Access-Control-Max-Age", MAX_AGE);
//            headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
//            headers.add("Access-Control-Expose-Headers", ALLOWED_Expose);
//            headers.add("Access-Control-Allow-Credentials", "true");
//            if (request.getMethod() == HttpMethod.OPTIONS) {
//                response.setStatusCode(HttpStatus.OK);
//                return Mono.empty();
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//
//}
