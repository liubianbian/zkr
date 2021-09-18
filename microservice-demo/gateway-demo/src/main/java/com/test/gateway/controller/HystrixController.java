package com.test.gateway.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HystrixController {

    @RequestMapping(value = "fallback", method = RequestMethod.GET)
    public String fallback() {
        return ( "网关请求异常，进入降级方法");
    }

    @HystrixCommand(commandKey = "fallbackCommand")
    public void fallbackCommand() {
        log.info("熔断器fallbackCommand被触发了");
    }
}
