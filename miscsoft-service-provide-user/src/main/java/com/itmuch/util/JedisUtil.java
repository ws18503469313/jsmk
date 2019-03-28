package com.itmuch.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {
	
	@Autowired
	private JedisPool jedisPool;
	
	private Jedis getResource() {
		return jedisPool.getResource();
	}
	
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = getResource();
		try {
			jedis.set(key, value);
			return value;
		} finally {
			jedis.close();
		}
		
	}
	public String setWithExpire(String key,String value, int time) {
		Jedis jedis = getResource();
		try {
			jedis.set(key, value, SET_SCHAME, TIME_UNIT, time);
			return value;
		} finally {
			jedis.close();
		}
		
	}
	/**
	 * 不存在时才set
	 */
	private static final String SET_SCHAME = "NX";
	/**
	 * 秒
	 */
	private static final String TIME_UNIT = "EX";
	public void expire(byte[] key, int i) {
		Jedis jedis = getResource();
		try {
			jedis.expire(key, i);
		} finally {
			jedis.close();
		}
	}

	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		Jedis jedis = getResource();
		try {
			return jedis.get(key);
		} finally {
			jedis.close();
		}
	}

	public void del(byte[] key) {
		Jedis jedis = getResource();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	public Set<byte[]> keys(String shiroSessionPrifix) {
		
		Jedis jedis = getResource();
		try {
			return jedis.keys((shiroSessionPrifix+"*").getBytes());
		} finally {
			jedis.close();
		}
	}
}
