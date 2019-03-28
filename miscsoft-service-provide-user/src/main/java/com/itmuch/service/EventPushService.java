package com.itmuch.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itmuch.Configuration.WXConfiguration;
import com.itmuch.model.wx.Content;
import com.itmuch.model.wx.Data;
import com.itmuch.model.wx.MessageConstant;
import com.itmuch.model.wx.TemplateMessage;
import com.itmuch.model.wx.WXTextMessage;
import com.itmuch.util.HttpClientUtil;
import com.itmuch.util.JedisUtil;
import com.itmuch.util.JsonUtils;
import com.itmuch.util.XMLUtils;

@Service
public class EventPushService {
	
	@Autowired
	private WXConfiguration wxConfiguration;
	@Autowired
	private JedisUtil jedisUtil;
	
	private static final String ACCESS_TOKEN_KEY = "SYKY_ACCESS_TOKEN_KEY";
	public String doEvent(HttpServletRequest req) throws Exception {
		String accessToken = getAccessToken();
		TEMPALTE_URL = TEMPALTE_URL.replace("ACCESS_TOKEN", accessToken);
		List<String> openIDs = Lists.newArrayList("oYqzm5TQOoLAOMO0miYzVQ3MwqyA");
//		HttpClientUtil.doPostJson(TEMPALTE_URL, templateMessage(openIDs));
		WXTextMessage message = XMLUtils.xmlToPojo(WXTextMessage.class, req);
		WXTextMessage result = new WXTextMessage();
		if(MessageConstant.REQ_MESSAGE_TYPE_TEXT.equals(message.getMsgType())) {
			
			result.setFromUserName(message.getToUserName());
			result.setToUserName(message.getFromUserName());
			result.setContent("hello world");
			result.setMsgId(message.getMsgId());
			result.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
			result.setMsgType(MessageConstant.REQ_MESSAGE_TYPE_TEXT);
		}
		return XMLUtils.pojoToXML(result);
	}
//	oYqzm5TQOoLAOMO0miYzVQ3MwqyA wangshuai的openid
	public static void main(String[] args) {
		
		
	}
	/**
	 * 获取微信公众号的ACCESS_TOKEN
	 * 先从redis中获取,没有/失效了再从wx服务器获取,再保存到redis中
	 * @return
	 */
	private String getAccessToken() {
		String accessToken = null;
		accessToken = String.valueOf(jedisUtil.get(ACCESS_TOKEN_KEY.getBytes()));
		if(accessToken != null) {
			return accessToken;
		}
		String url = "https://api.weixin.qq.com/cgi-bin/token";
//		grant_type=client_credential&appid=APPID&secret=APPSECRET
		Map<String,String> params = Maps.newHashMap();
		params.put("grant_type", "client_credential");
		params.put("appid", wxConfiguration.getSyAppid());
		params.put("secret", wxConfiguration.getSySecret());
		
		String result = HttpClientUtil.doGet(url, params);
		
		JSONObject json = JSON.parseObject(result);
		
		accessToken = json.get("access_token").toString();
		jedisUtil.setWithExpire(ACCESS_TOKEN_KEY, accessToken, json.getIntValue("expires_in"));
		return accessToken;
	}
	/**
	 * 调用微信模板的url
	 */
	private static String TEMPALTE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	/**
	 * 发送模板消息
	 */
	private String templateMessage(List<String> openIDs) {
		Content first = new Content("first", "#FF0000");
		Content keyword1 = new Content("keyword1", "#FF0000");
		Content keyword2 = new Content("keyword2", "#FF0000");
		Content keyword3 = new Content("keyword3", "#FF0000");
		Content remark = new Content("remark", "#FF0000");
		Data data = new Data(first, keyword1, keyword2, keyword3, remark);
		
		
		TemplateMessage message = new TemplateMessage();
		message.setTemplateID(wxConfiguration.getTemplate());
		message.setUserOpenID(openIDs.get(0));
		message.setUrl("www.baidu.com");
		message.setData(data);
		return JsonUtils.objectToJson(message);
	
	}
}
