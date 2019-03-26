package com.itmuch.controller.wx;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.itmuch.Configuration.WXConfiguration;
import com.itmuch.util.JSONResult;

@RestController
@RequestMapping(value= {"/wx"})
public class WXController {
	
	@Value("${wx.secret}")
	private String secret;
	@Value("${wx.appid}")
	private String appid;
	
	private static final String GRANT_TYPE = "authorization_code";
	
	private static final Logger log = LoggerFactory.getLogger(WXController.class);
	@RequestMapping("/onLogin")
	public JSONResult onLogin(String code) {
		log.debug("coed :"+ code);
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> params = Maps.newHashMap();
		params.put("appid", appid);
		params.put("secret", secret);
		params.put("js_code", code);
		params.put("grant_type", GRANT_TYPE);
		
		return JSONResult.ok();
	}
}
