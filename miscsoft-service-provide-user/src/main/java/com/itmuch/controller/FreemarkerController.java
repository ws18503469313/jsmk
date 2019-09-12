package com.itmuch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itmuch.model.Resource;
import com.cloud.util.JSONResult;
import com.itmuch.util.JedisUtil;

import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("ftl")
public class FreemarkerController {
	
	@Autowired
	private Resource resource;
	
	@Autowired
	private JedisUtil jedisUtil;
	@RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("resource", resource);
        return "freemarker/center";
    }
	
	
	@RequestMapping("/getErr")
	public JSONResult getErr() {
		
		int i = 1/0;
		System.out.println(i);
		return  JSONResult.ok();
	}
	@RequestMapping("/testRedis")
	@ResponseBody
	public JSONResult testRedis(String key) {
		jedisUtil.set("mmp".getBytes(), "ccacacaca".getBytes());
		Object value = jedisUtil.get("mmp".getBytes());
		
		return JSONResult.ok(value);
	}
}
