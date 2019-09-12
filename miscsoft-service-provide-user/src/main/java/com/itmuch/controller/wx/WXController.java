package com.itmuch.controller.wx;

import com.alibaba.fastjson.JSON;
import com.cloud.dto.LocationDTO;
import com.cloud.model.User;
import com.cloud.model.WXResult;
import com.cloud.util.CheckUtils;
import com.cloud.util.HttpClientUtil;
import com.cloud.util.JSONResult;
import com.cloud.util.JsonUtils;
import com.google.common.collect.Maps;
import com.itmuch.cache.RedisCache;
import com.itmuch.controller.BaseController;
import com.itmuch.service.EventPushService;
import com.itmuch.service.UserService;
import com.itmuch.service.WXResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
	@Autowired
	private RedisCache redisCache;
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
	 * 获取用户的access_token
	 * @param code
	 * @param user
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/getAccessToken")
	@ResponseBody
	@Transactional
	public JSONResult onLogin(String code,User user, HttpServletRequest req, HttpServletResponse resp) {
		//2019-7-19考虑一进入小程序,请求获取用户access_token,自动发送地址,不需要登陆环节
		//**注释开始
		//在系统中登陆**/
		/*Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		subject.login(token);
		req.getSession().setAttribute("user", getCurrentUser());*/


		//将登陆信息保存到数据库
//		Md5Hash thirdKey = new Md5Hash(user.getPassword(), user.getUsername(), 2);
//		result.setUserId(getCurrentUser().getId());
//		result.setThirdKey(thirdKey.toString());
//		wxResultService.save(result);

		//将登陆凭证返回给客户
		//小程序貌似不支持cookie
//		Cookie cookie = new Cookie("thirdKey", thirdKey.toString());
//		cookie.setMaxAge(60*60*24*30*365);
//		resp.addCookie(cookie);


//		JSONObject loginToken = new JSONObject();
//		loginToken.put("id",result.getOpenid());
//		loginToken.put("secrit", thirdKey.toString());
		//**注释结束**/
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

		log.info(result.toString());

		return JSONResult.ok(result.getOpenid());
	}

	/**
	 * 用户访问小程序上传位置信息
	 * @param locationDTO
	 */
	@RequestMapping("/postAddress")
	@ResponseBody
	public void postAddress(LocationDTO locationDTO){
//		redisCache.put("")
		System.out.println(JSON.toJSONString(locationDTO));
	}
}
