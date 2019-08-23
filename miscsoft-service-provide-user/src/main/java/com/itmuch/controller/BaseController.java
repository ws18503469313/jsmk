package com.itmuch.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.itmuch.util.HttpClientUtil;
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
//	@ResponseBody
	public void ajaxLogin(User user, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		JSONObject obj = HttpClientUtil.getIpInfo(req, null);
		obj.put("username", user.getUsername());
		log.info("====================登陆信息:{}",  obj.toJSONString());
		req.getSession().setAttribute("user", getCurrentUser());
//		return JSONResult.ok(getCurrentUser());
		String result = JSONResult.ok(getCurrentUser()).toString();
		//前端传过来的回调函数名称
		String callback = req.getParameter("callback");
		//用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
		result = callback + "(" + result + ")";
		resp.getWriter().write(result);
	}
	public void systemLogin(User user){

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
