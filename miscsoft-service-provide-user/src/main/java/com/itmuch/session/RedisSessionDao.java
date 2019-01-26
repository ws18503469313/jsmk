package com.itmuch.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.itmuch.util.JedisUtil;
@Component
public class RedisSessionDao extends AbstractSessionDAO{
	
	private static final Logger log = LoggerFactory.getLogger(RedisSessionDao.class);
	@Autowired
	private JedisUtil jedisUtil;
	
	private static final String SHIRO_SESSION_PRIFIX = "ws-session:";
			
	private byte[] getKey(String key) {
		return (SHIRO_SESSION_PRIFIX+key).getBytes();
	}
	
	private void saveSession(Session session) {
		log.info("----------------------------save session to redis -------------------------");
		if(session != null && session.getId() != null) {
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			jedisUtil.set(key,value);
			jedisUtil.expire(key,600);
		}
	}
	
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
		
	}

	public void delete(Session session) {
		log.info("----------------------------destory session to redis -------------------------");
		if(session == null || session.getId() == null) {
			return ;
		}
		byte[] key = getKey(session.getId().toString());
		jedisUtil.del(key);
	}

	public Collection<Session> getActiveSessions() {
		
		Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PRIFIX);
		Set<Session> sessions = new HashSet<Session>();
		if(keys == null) {
			return sessions;
		}
		for (byte[] key : keys) {
			sessions.add((Session) SerializationUtils.deserialize(jedisUtil.get(key)));
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		log.info("----------------------------create session to redis -------------------------");
		Serializable sessionId = generateSessionId(session);
		saveSession(session);
		assignSessionId(session, sessionId);
		
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		
		if(sessionId == null) {
			return null;
		}
		
		byte[] key = getKey(sessionId.toString());
		byte[] value = jedisUtil.get(key);
		System.out.println("sessionId"+sessionId);
		
		return (Session) SerializationUtils.deserialize(value);
	}
	

}
