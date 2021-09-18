package com.test.demo.controller;


import com.test.demo.bean.User;
import com.test.demo.feign.ICoreFeignClient;
import com.test.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private ICoreFeignClient coreFeignClient;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 读取apollo配置文件接口，可观察在apollo做配置变动的时候
     * 对应配置是否动态读取
     * @return
     */
    @RequestMapping(value = "readApolloProperty", method = RequestMethod.GET)
    public String readApolloProperty() {
        return coreFeignClient.readApolloProperty();
    }

    /**
     * 查询用户接口
     * 该接口主要查看feign调用单参数实例
     * @return
     */
    @RequestMapping(value = "queryUser", method = RequestMethod.GET)
    public String queryUser() {
        return coreFeignClient.queryUserByUserId("szk001");
    }

    /**
     * 多条件查询用户信息
     * @param user
     * @param msg
     * @return
     */
    @RequestMapping(value = "queryUserByCondition", method = RequestMethod.POST)
    public String queryUserByCondition(User user, String msg) {
        return coreFeignClient.queryUserListByCondition(user, msg);
    }

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value = "queryUserList", method = RequestMethod.GET)
    public String queryUserList() {
        return coreFeignClient.queryUserList();
    }

    /**
     * 添加用户接口
     * 该接口主要查看feign调用对象参数实例
     * @param user
     * @return
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addUser(User user) {
        return coreFeignClient.addUser(user);
    }

    /**
     * 修改用户接口
     * 该接口主要查看feign调用对象参数实例
     * @param user
     * @return
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public String updateUser(User user) {
        return coreFeignClient.updateUser(user);
    }

    /**
     * 删除用户接口
     * @param userId
     * @return
     */
    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public String deleteUser(String userId) {
        return coreFeignClient.deleteUser(userId);
    }

    /**
     * 测试feign降级接口
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "testFeignHystrix", method = RequestMethod.GET)
    public String testFeignHystrix() throws Exception {
        return coreFeignClient.testHystrix();
    }

    /**
     * 测试网关降级过滤器接口
     * 当edge边缘包调用失败的时候，会进入网关降级接口
     * @return
     */
    @RequestMapping(value = "testGatewayHystrix", method = RequestMethod.GET)
    public String testGatewayHystrix() throws Exception {
        log.info("请求进入测试网关降级过滤器方法");
        return coreFeignClient.testHystrix();
    }


    /**
     * redis 测试
     * @return
     */
    @RequestMapping(value = "redisAddTest", method = RequestMethod.GET)
    public String redisAddTest() throws Exception {
        log.info("redis 添加方法");
        redisUtil.set(0,"test","测试数据");
        return redisUtil.get(0,"test")+":返回";
    }

}
