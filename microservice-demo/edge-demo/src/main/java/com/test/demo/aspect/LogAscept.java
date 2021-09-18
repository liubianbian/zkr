package com.test.demo.aspect;


import com.test.demo.util.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LogAscept {

    /**
     * 定义AOP扫描路径
     * 拦截controller包下面的所有类中，有@RequestMapping注解的方法
     */
    @Pointcut("execution(* com.test.demo.controller..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void log(){}

    /**
     * 记录HTTP请求开始时的日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        log.info("请求开始,{}", joinPoint.toString());
    }

    /**
     * 拦截service层异常，记录异常日志，并设置对应的异常信息
     * 目前只拦截Exception，是否要拦截Error需再做考虑
     * @param e 异常对象
     */
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void handle(JoinPoint point, Exception e) {
        log.info("error,{}",point.toString());
        return;
    }

    /**
     * 打印返回内容
     * @param object 响应报文数据
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object){
        log.info("after：{}", FastJsonUtil.toJson(object));
    }

}
