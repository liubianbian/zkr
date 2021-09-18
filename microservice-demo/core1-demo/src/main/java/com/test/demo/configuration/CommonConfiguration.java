//package com.cignacmb.demo.configuration;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.MultipartConfigElement;
//
///**
// * 公共配置类
// *
// */
//@Configuration
//public class CommonConfiguration {
//
//    /**
//     * 设置文件上传大小
//     * @return
//     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//    	MultipartConfigFactory factory = new MultipartConfigFactory();
//    	//单个文件最大容量
//    	factory.setMaxFileSize("500MB");
//    	//一次请求最大容量
//    	factory.setMaxRequestSize("1000MB");
//    	return factory.createMultipartConfig();
//    }
//}
