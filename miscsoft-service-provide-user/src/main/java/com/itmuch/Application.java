package com.itmuch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableWebMvc
@EnableAsync
//@EnableScheduling
// 扫描 mybatis mapper 包路径
@MapperScan(basePackages = "com.itmuch.mapper")
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages = { "com.itmuch", "org.n3r.idworker" })
@EnableEurekaClient
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);


	public static void main(String[] args) {
//		long start = System.currentTimeMillis();
		SpringApplication.run(Application.class, args);
//		log.info("started, took o.o.o.o.o.O" + (System.currentTimeMillis() - start) + "ms");
	}

	// @Bean
	// public InternalResourceViewResolver jspViewResolver() {
	// InternalResourceViewResolver resolver= new InternalResourceViewResolver();
	// resolver.setPrefix("/WEB-INF/jsp/");
	// resolver.setSuffix(".jsp");
	// return resolver;
	// }
	// jsp页面的视图解析器，解析到webapp下的jsp/目录下查找对应的jsp页面
}
