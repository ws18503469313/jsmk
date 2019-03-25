package com.itmuch.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:wx.properties")
public class WXConfiguration {
	
	@Value("${wx.secret}")
	private String secret;
	@Value("${wx.appid}")
	private String appid;
}
