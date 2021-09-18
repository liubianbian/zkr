package com.test.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableApolloConfig({"application","TEST1.datasource"})
@MapperScan("com.test.*.dao.**")
public class TestCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCoreApplication.class, args);
	}

}
