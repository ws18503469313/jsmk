package com.itmuch.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.itmuch.model.User;
import com.itmuch.service.AccessService;
import com.itmuch.service.RoleService;
import com.itmuch.util.JSONResult;

@Controller
@RequestMapping("/main/")
public class BaseController extends CoreController{
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AccessService accessService;
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 登录方法
	 * @param user
	 * @return
	 */
	@RequestMapping("doLogin")
	public String doLogin(User user, HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		req.getSession().setAttribute("user", getCurrentUser());
		return "redirect:/main/index";
	}	
	@RequestMapping("ajaxLogin")
	@ResponseBody
	public JSONResult ajaxLogin(User user, HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		req.getSession().setAttribute("user", getCurrentUser());
		return JSONResult.ok(getCurrentUser());
	}
	/**
	 * 返回主页,并返回权限树
	 * @return
	 */
	@RequestMapping(value="index")
	public String index(Map<String, Object> map) {
		JSONArray tree = accessService.getSysAccessTree(getCurrentUser().getId(), null);
		map.put("tag", tree);
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
	
	@RequestMapping("/shiroTag")
	public String shiroTag() {
		return "shiro";
	}
	
}
