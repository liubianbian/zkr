package com.test.demo;

import com.test.demo.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCoreApplicationTests {

	@Autowired
	TestService applicationLocation;

	@Test
	public void contextLoads() {
		System.out.println(applicationLocation.queryUserList());
	}

}
