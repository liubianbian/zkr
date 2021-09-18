package com.test.demo.controller;


import com.test.demo.bean.User;
import com.test.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiUserController {

//    @Value("${application.location}")
    private String applicationLocation;

    @Autowired
    private TestService userService;


    /**
     * 修改字段之后 读取apollo配置
     * 看是否变化
     */
    @RequestMapping(value = "/user/readApolloProperty", method = RequestMethod.GET)
    public String readApolloProperty() {
        log.info("readApolloProperty接口被请求了,type:{}", "core1");
        return ("修改的字段值为：" + applicationLocation);
    }

    /**
     * 根据userId查询用户信息
     * 简单参数传递
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/queryUserByUserId", method = RequestMethod.GET)
    public String queryUserByUserId(@RequestParam("userId") String userId) {
        log.info("queryUserByUserId接口被请求了,type:{}", "core1");
        return userService.queryUserByUserId(userId);
    }

    /**
     * 查询用户列表
     * 复杂参数传递
     * @param params
     * @param msg
     * @return
     */
    @RequestMapping(value = "/user/queryUserListByCondition", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String queryUserList(@RequestBody User user, @RequestParam("msg") String msg) {
        log.info("进入查询用户列表接口,type:{}", "core1");
        return userService.queryUserListByCondition(msg);
    }

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value = "/user/queryUserList", method = RequestMethod.GET)
    public String queryUserList() {
        log.info("queryUserList进入查询用户列表接口,type:{}", "core1");
        return userService.queryUserList();
    }

    /**
     * 添加用户
     * 此处如果是一个对象传递的话，@RequestBody注解可以不用写，feign会自动加上
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody User user) {
        log.info("addUser进入查询用户列表接口,type:{}", "core1");
        return userService.addUser(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody User user) {
        log.info("updateUser进入查询用户列表接口,type:{}", "core1");
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/deleteUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@RequestParam("userId") String userId) {
        log.info("deleteUser进入查询用户列表接口,type:{}", "core1");
        return userService.deleteUser(userId);
    }

    /**
     * 测试熔断
     * @return
     */
    @RequestMapping(value = "/user/testHystrix", method = RequestMethod.GET)
    public String testHystrix() throws Exception {
        log.info("进入测试熔断方法,type:{}", "core1");
        Thread.sleep(500000000);
        return userService.queryUserList();
    }


}
