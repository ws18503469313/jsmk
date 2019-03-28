package com.itmuch.model.wx;
/**
 * 一条模板消息
 * @author polunzi
 *
 */
public class TemplateMessage {
	/**
	 * 要给发送的用户微信openID
	 */
	private String userOpenID;
	/**
	 * 使用的模板id
	 */
	private String templateID;
	/**
	 * 详情跳转的接口
	 */
	private String url;
	/**
	 * 发送的具体消息内容
	 */
	private Data data;
	public String getUserOpenID() {
		return userOpenID;
	}
	public void setUserOpenID(String userOpenID) {
		this.userOpenID = userOpenID;
	}
	public String getTemplateID() {
		return templateID;
	}
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
