package com.itmuch.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmuch.model.User;

@Controller
@RequestMapping("/main/")
public class BaseController {
	
	/**
	 * 登录方法
	 * @param user
	 * @return
	 */
	@RequestMapping("doLogin")
	public String doLogin(User user) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		return "redirect:/main/index";
	}
	/**
	 * 返回主页
	 * @return
	 */
	@RequestMapping(value="index")
	public String index() {
		return "main";
	}
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/main/login";
	}
	
}
