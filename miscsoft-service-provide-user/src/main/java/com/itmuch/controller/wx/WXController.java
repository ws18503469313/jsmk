package com.itmuch.controller.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.itmuch.controller.BaseController;
import com.itmuch.model.User;
import com.itmuch.model.WXResult;
import com.itmuch.service.EventPushService;
import com.itmuch.service.UserService;
import com.itmuch.service.WXResultService;
import com.itmuch.util.CheckUtils;
import com.itmuch.util.HttpClientUtil;
import com.itmuch.util.JSONResult;
import com.itmuch.util.JsonUtils;

@RestController
@RequestMapping(value= {"/wx"})
public class WXController extends BaseController{
	
	@Value("${wx.secret}")
	private String secret;
	@Value("${wx.appid}")
	private String appid;
	
	@Autowired
	private UserService userService;
	@Autowired
	private WXResultService wxResultService;
	@Autowired
	private EventPushService eventPushService;
	private static final String GRANT_TYPE = "authorization_code";
	
	
	private static final Logger log = LoggerFactory.getLogger(WXController.class);
	
	/**
	 * 微信公众号开启微信开发者模式校验方法
	 * @param signature	
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkSignature",  method = RequestMethod.GET)
	public void checkSignature(String echostr, HttpServletRequest req,HttpServletResponse resp) throws IOException {
		PrintWriter writer = resp.getWriter();
		if (CheckUtils.checkSignature(req)) {
			writer.write(echostr);
		}
		
	}
	
	@RequestMapping(value = "/checkSignature",  method = RequestMethod.POST)
	public void eventpush(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.判断推动的数据类型
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		log.info("==================eventpush:POST========================");

		PrintWriter out = resp.getWriter();

		if (CheckUtils.checkSignature(req)) {

			String message = null;
			try {
				message = eventPushService.doEvent(req);
			} catch (Exception e) {
				message = "sorry world";
				e.printStackTrace();
			}

		out.write(message);

		} else {

		out.write("error");

		}
	}
	/**
	 * 微信小程序登陆方法
	 * @param code
	 * @param user
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/onLogin")
	@ResponseBody
	@Transactional
	public JSONResult onLogin(String code,User user, HttpServletRequest req, HttpServletResponse resp) {
		//在系统中登陆
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		req.getSession().setAttribute("user", getCurrentUser());
		//判断有没有绑定
//		WXResult exist = wxResultService.
//		if(exist == null) {
//			
//		}
		//微信获取sessionkey和openid
		log.info("coed :"+ code);
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> params = Maps.newHashMap();
		params.put("appid", appid);
		params.put("secret", secret);
		params.put("js_code", code);
		params.put("grant_type", GRANT_TYPE);
		String json = HttpClientUtil.doGet(url, params);
		WXResult result = JsonUtils.jsonToPojo(json, WXResult.class);
		
		
		//将登陆信息保存到数据库
		Md5Hash thirdKey = new Md5Hash(user.getPassword(), user.getUsername(), 2);
		result.setUserId(getCurrentUser().getId());
		result.setThirdKey(thirdKey.toString());
//		wxResultService.save(result);
		
		log.info(result.toString());
		
		//将登陆凭证返回给客户
		Cookie cookie = new Cookie("thirdKey", thirdKey.toString());
		cookie.setMaxAge(60*60*24*30*365);
		resp.addCookie(cookie);
		return JSONResult.ok(thirdKey.toString());
	}
}
