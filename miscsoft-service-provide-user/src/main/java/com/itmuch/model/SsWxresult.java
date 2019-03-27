package com.itmuch.model;

import javax.persistence.*;

@Table(name = "ss_wxresult")
public class SsWxresult {
    /**
     * sys_user的id,和用户微信绑定的id
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 从微信服务器获取的session_key
     */
    @Column(name = "session_key")
    private String sessionKey;

    /**
     * 从微信服务器获取的openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 保存在客户端的登陆凭证
     */
    @Column(name = "third_key")
    private String thirdKey;

    /**
     * 获取sys_user的id,和用户微信绑定的id
     *
     * @return user_id - sys_user的id,和用户微信绑定的id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置sys_user的id,和用户微信绑定的id
     *
     * @param userId sys_user的id,和用户微信绑定的id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取从微信服务器获取的session_key
     *
     * @return session_key - 从微信服务器获取的session_key
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * 设置从微信服务器获取的session_key
     *
     * @param sessionKey 从微信服务器获取的session_key
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * 获取从微信服务器获取的openId
     *
     * @return open_id - 从微信服务器获取的openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置从微信服务器获取的openId
     *
     * @param openId 从微信服务器获取的openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取保存在客户端的登陆凭证
     *
     * @return third_key - 保存在客户端的登陆凭证
     */
    public String getThirdKey() {
        return thirdKey;
    }

    /**
     * 设置保存在客户端的登陆凭证
     *
     * @param thirdKey 保存在客户端的登陆凭证
     */
    public void setThirdKey(String thirdKey) {
        this.thirdKey = thirdKey;
    }
}