package com.test.demo;

import com.test.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: luofang
 * @create: 2020-02-11 11:07
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void setTest(){
        redisUtil.set(0,"12","23");
        System.out.println(redisUtil.get(0,"12"));
    }
}
