package com.test.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

/**
 * util
 */
@Slf4j
public class WebUtil {

    /**
     * 输出响应内容
     * @param exchange 请求上下文
     * @return 响应内容
     */
    public static Mono<Void> writeResponse(ServerWebExchange exchange, Object object) {
        String resData = FastJsonUtil.toJson(object);
        exchange.getAttributes().put("responseBodyContent", resData);
        byte[] bytes = resData.getBytes(StandardCharsets.UTF_8);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }


}
