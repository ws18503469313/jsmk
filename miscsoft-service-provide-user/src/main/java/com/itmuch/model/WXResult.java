package com.itmuch.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author polunzi
 */
@Table(name="ss_wxresult")
public class WXResult extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id,等同于数据库表中的Userid
	 */
	@Id
    @Column(name = "user_id")
	private String userId;
	@Column(name = "session_key")
	private String session_key;
	/**
     * 从微信服务器获取的openId
     */
    @Column(name = "open_id")
	private String openid;
	@Column(name="third_key")
    private String thirdKey;
    
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getThirdKey() {
		return thirdKey;
	}
	public void setThirdKey(String thirdKey) {
		this.thirdKey = thirdKey;
	}
	

	
	
}
