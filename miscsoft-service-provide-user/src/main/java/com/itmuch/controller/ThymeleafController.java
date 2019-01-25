package com.itmuch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmuch.model.User;

@Controller
@RequestMapping("th")
public class ThymeleafController {

	@RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("name", "thymeleaf-imooc");
        return "thymeleaf/index";
    }
	
	@RequestMapping("center")
    public String center() {
        return "thymeleaf/center/center";
    }
	
	
	
	@PostMapping("postform")
    public String postform(User u) {
		
		System.out.println("姓名：" + u.getName());
		System.out.println("年龄：" + u.getAge());
		
        return "redirect:/th/test";
    }
	
	@RequestMapping("showerror")
    public String showerror(User u) {
		
		int a = 1 / 0;
		System.out.println(a);
        return "redirect:/th/test";
    }
}