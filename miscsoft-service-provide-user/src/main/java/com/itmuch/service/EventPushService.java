package com.itmuch.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.util.HttpClientUtil;
import com.cloud.util.JsonUtils;
import com.cloud.util.xmlreader.XMLUtils;
import com.google.common.collect.Maps;
import com.itmuch.Configuration.WXConfiguration;
import com.itmuch.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class EventPushService {
	
	
	private static final Logger log = LoggerFactory.getLogger(EventPushService.class);
	@Autowired
	private WXConfiguration wxConfiguration;
	@Autowired
	private JedisUtil jedisUtil;
	
	private static final String ACCESS_TOKEN_KEY = "SYKY_ACCESS_TOKEN_KEY";
	public String doEvent(HttpServletRequest req) throws Exception {
		
//		String accessToken = getAccessToken();
//		TEMPALTE_URL = TEMPALTE_URL.replace("ACCESS_TOKEN", accessToken);
//		log.info("o.o.o.o.o.O.ACCESS_TOKEN:"+accessToken+"------------------------");
//		HttpClientUtil.doPostJson(TEMPALTE_URL, templateMessage(openIDs));
		com.cloud.model.wx.WXTextMessage message = XMLUtils.xmlToPojo(com.cloud.model.wx.WXTextMessage.class, req);
//		Map<String, String> map = XMLUtils.xmltoMap(req);
		com.cloud.model.wx.WXTextMessage result = new com.cloud.model.wx.WXTextMessage();
		if(com.cloud.model.wx.MessageConstant.REQ_MESSAGE_TYPE_TEXT.equals(message.getMsgType())) {
			
			result.setFromUserName(message.getToUserName());
			result.setToUserName(message.getFromUserName());
			result.setContent(tuLinAPI(message.getContent()));
			result.setMsgId(message.getMsgId());
			result.setCreateTime(String.valueOf(System.currentTimeMillis()/1000));
			result.setMsgType(com.cloud.model.wx.MessageConstant.REQ_MESSAGE_TYPE_TEXT);
		}
		log.info("o.o.o.o.O.return result:"+result.toString()+"-----------------------");
		return XMLUtils.pojoToXML(result);
	}
	
	private static final String APIKEY = "5e673f7ac02741899fa27ec8c160b147";
	/**
	 * 调取图灵接口
	 * @param quesiton
	 * @return
	 * @throws IOException
	 */
	private static String tuLinAPI(String quesiton) throws IOException {
        //接入机器人，输入问题
        
        String INFO = URLEncoder.encode(quesiton, "utf-8");//这里可以输入问题
        String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
//		String getURL = "http://www.wssmjy.com";
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();

        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        String[] ss = new String[10];
        String s = sb.toString();
        String answer;
	     ss = s.split(":");
	    answer = ss[ss.length-1];
	    answer = answer.substring(1,answer.length()-2);
	    return answer;
    }
	public static void main(String[] args) {
		EventPushService service = new EventPushService();
		service.getAccessToken();
	}
	/**
	 * 获取微信公众号的ACCESS_TOKEN
	 * 先从redis中获取,没有/失效了再从wx服务器获取,再保存到redis中
	 * @return
	 */
	private String getAccessToken() {
		String accessToken = null;
//		accessToken = String.valueOf(jedisUtil.get(ACCESS_TOKEN_KEY.getBytes()));
//		if(accessToken != null) {
//			return accessToken;
//		}
		String url = "https://api.weixin.qq.com/cgi-bin/token";
//		grant_type=client_credential&appid=APPID&secret=APPSECRET
		Map<String,String> params = Maps.newHashMap();
		params.put("grant_type", "client_credential");
		params.put("appid", "wx3f4e6b31d77b67b8");
		params.put("secret", "aca8745f36fd6b8a44e64443407dc568");
//		params.put("appid", wxConfiguration.getSyAppid());
//		params.put("secret", wxConfiguration.getSySecret());
		
		String result = HttpClientUtil.doGet(url, params);
		
		JSONObject json = JSON.parseObject(result);
		
		accessToken = json.get("access_token").toString();
		log.info("o.o.o.o.O.调取wx接口获取的结果"+json.toString());
		jedisUtil.setWithExpire(ACCESS_TOKEN_KEY, accessToken, Integer.valueOf(json.get("expires_in").toString()));
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
		com.cloud.model.wx.Content first = new com.cloud.model.wx.Content("first", "#FF0000");
		com.cloud.model.wx.Content keyword1 = new com.cloud.model.wx.Content("keyword1", "#FF0000");
		com.cloud.model.wx.Content keyword2 = new com.cloud.model.wx.Content("keyword2", "#FF0000");
		com.cloud.model.wx.Content keyword3 = new com.cloud.model.wx.Content("keyword3", "#FF0000");
		com.cloud.model.wx.Content remark = new com.cloud.model.wx.Content("remark", "#FF0000");
		com.cloud.model.wx.Data data = new com.cloud.model.wx.Data(first, keyword1, keyword2, keyword3, remark);
		
		
		com.cloud.model.wx.TemplateMessage message = new com.cloud.model.wx.TemplateMessage();
		message.setTemplateID(wxConfiguration.getTemplate());
		message.setUserOpenID(openIDs.get(0));
		message.setUrl("www.baidu.com");
		message.setData(data);
		return JsonUtils.objectToJson(message);
	
	}
}
