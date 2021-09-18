package com.test.demo.feign.hystrix;


import com.test.demo.bean.User;
import com.test.demo.feign.ICoreFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * feign熔断类
 * 该类需要声明为组件给spring进行管理
 */
@Slf4j
@Component
public class CoreFeignHystrix implements ICoreFeignClient {

    @Override
    public String readApolloProperty() {
        return fallback();
    }

    @Override
    public String queryUserByUserId(String userId) {
        return fallback();
    }

    @Override
    public String queryUserListByCondition(User user, String msg) {
        return fallback();
    }

    @Override
    public String queryUserList() {
        return fallback();
    }

    @Override
    public String addUser(User user) {
        return fallback();
    }

    @Override
    public String updateUser(User user) {
        return fallback();
    }

    @Override
    public String deleteUser(String userId) {
        return fallback();
    }

    @Override
    public String testHystrix() {
        return fallback();
    }

    private String fallback() {
        log.info("进入熔断降级方法");
        log.info("方法" + Thread.currentThread().getStackTrace()[2].getMethodName() + "调用失败,进入熔断降级方法");
        return  "方法" + Thread.currentThread().getStackTrace()[2].getMethodName() + "调用失败,进入熔断降级方法";
    }
}
