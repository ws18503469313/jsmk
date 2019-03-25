package com.itmuch.controller.wx;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.itmuch.util.JSONResult;

@RestController
@RequestMapping(value= {"/wx"})
public class WXController {
	
	
	private static final Logger log = LoggerFactory.getLogger(WXController.class);
	@RequestMapping("/onLogin")
	public JSONResult onLogin(String code) {
		log.debug("coed :"+ code);
		String url = "https://api.weixin.qq.com/sns/jscode2session";
//		?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
		Map<String, String> params = Maps.newHashMap();
		params.put("appid", "wx1cafde6da508b631");
		params.put("secret", "c9c2fa884497ee352e9e1aaa1e6ff0ca");
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		return JSONResult.ok();
	}
}
