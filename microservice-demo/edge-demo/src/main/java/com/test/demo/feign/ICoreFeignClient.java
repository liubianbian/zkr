package com.test.demo.feign;

import com.test.demo.bean.User;
import com.test.demo.configuration.FeignConfiguration;
import com.test.demo.feign.hystrix.CoreFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * feign调用客户端
 * name：为调用提供方的服务名
 * configuration： 为feign配置类，主要做消息转换配置和文件上传（兼容多文件上传）配置
 * fallback：为feign熔断类，当提供方调用接口调不通的情况下，进入对应的熔断方法
 */
@FeignClient(name = "core1-demo", configuration = FeignConfiguration.class,
        fallback = CoreFeignHystrix.class)
public interface ICoreFeignClient {

    /**
     * 修改字段之后 读取apollo配置
     * 看是否变化
     */
    @RequestMapping(value = "/user/readApolloProperty", method = RequestMethod.GET)
    String readApolloProperty();

    /**
     * 根据用户id查询用户信息
     * 简单参数传递
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/queryUserByUserId", method = RequestMethod.GET)
    String queryUserByUserId(@RequestParam("userId") String userId);

    /**
     * 查询用户集合列表
     * 复杂参数传递
     * @param params
     * @param msg
     * @return
     */
    @RequestMapping(value = "/user/queryUserListByCondition", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String queryUserListByCondition(@RequestBody User user, @RequestParam("msg") String msg);

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value = "/user/queryUserList", method = RequestMethod.GET)
    String queryUserList();

    /**
     * 添加用户
     * 此处如果是一个对象传递的话，@RequestBody注解可以不用写，feign会自动加上
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String addUser(@RequestBody User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    String updateUser(@RequestBody User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/deleteUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    String deleteUser(@RequestParam("userId") String userId);

    /**
     * 测试熔断
     * @return
     */
    @RequestMapping(value = "/user/testHystrix", method = RequestMethod.GET)
    String testHystrix();

}
