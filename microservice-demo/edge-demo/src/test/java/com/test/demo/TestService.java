package com.test.demo;

import com.test.demo.feign.ICoreFeignClient;
import com.test.demo.service.FunctionInterfaceTest;
import com.test.demo.util.FastJsonUtil;
import com.test.demo.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luofang
 * @create: 2019-08-21 14:43
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {

    @Autowired
    private ICoreFeignClient coreFeignClient;

    @Test
    public void  test(){
        log.info(coreFeignClient.queryUserList());

        Map<String, Object> reqMap = new HashMap<String, Object>(16);
        reqMap.put("manageCom", "8601000001");
        reqMap.put("currentPage", 1);
        reqMap.put("pageSize", 100);
        reqMap.put("requestSource", "1");
        reqMap.put("type", "I");
        reqMap.put("name", "张践仍");

//        String url = "http://localhost:18103/ibss-core-baisp/interface/queryPolicyList";
        String url = "http://ibss-uat.test-cignacmb.com/ibss-core-baisp/interface/queryPolicyList";
//        String resultString = HttpUtil.sendHttpPostJsonData(url, FastJsonUtil.toJson(reqMap));
        String resultString = HttpUtil.sendHttpPostJsonData(url, FastJsonUtil.toJson(reqMap));
        System.out.println(resultString);
    }

    public static void main(String[] args){


        FunctionInterfaceTest interfaceTest = item -> {
            String sk = item.toUpperCase();
            return sk + "***";
        };

//        FunctionInterfaceTest interfaceTest1 = Main::main;
        System.out.println(interfaceTest.getInfo("q"));

    }
}
