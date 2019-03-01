package com.itmuch.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerSessionManager extends DefaultWebSessionManager{
	
	
	private static final Logger log = LoggerFactory.getLogger(CustomerSessionManager.class);
	protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
		log.info("-------------------------start resove session ----------------------------");
		Serializable sessionId = getSessionId(sessionKey);
		
		ServletRequest request = null;
		
		if(sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey)sessionKey).getServletRequest();
		}
		if(request != null && sessionId != null){
			Session session = (Session)request.getAttribute(sessionId.toString());
			if(session != null) {
				log.info("--------------------------------get_session_from_request------------------------");
				return session;
			}
		}
		//request中没有session才到redis中获取session,并直接保存到request中
		
		Session session = 	super.retrieveSession(sessionKey);
		log.info("--------------------------------get_session_from_redis_and_save_to_request------------------------");
		if(request != null && sessionId != null) {
			request.setAttribute(sessionId.toString(),session);
		}
		
		return session;
				
		 
	}
	
	
}
