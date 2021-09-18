package com.test.demo.configuration;

import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * feign配置类
 * feign核心包依赖的版本很重要   现在用3.0.3可以     最新的3.5.0不可以
 */
//@Configuration
public class FeignConfiguration {

	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;
	
//	@Bean
//	@Primary
//	@Scope("prototype")
//	public Encoder feignEncoder() {
//		return new FeignFormEncoder(new SpringEncoder(messageConverters));
//	}

}
