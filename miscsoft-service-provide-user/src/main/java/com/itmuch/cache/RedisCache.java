package com.itmuch.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.itmuch.util.JedisUtil;
@Component
public class RedisCache<K,V> implements Cache<K, V>{
		
	private static final String CACHE_PREIX = "project-cache:";
	
	
	private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
	private byte[] getKey(K k) {
		if(k instanceof String) {
			return (CACHE_PREIX + k).getBytes();
		}
		
		return SerializationUtils.serialize(k);
	}
	
	@Autowired
	private JedisUtil jedisUtil;
	
	public V get(K key) throws CacheException {
//		log.info("-------------------------------------从redis中获取数据     ----------------------------------");
		byte[] value = jedisUtil.get(getKey(key));
		if(value != null) {
			return (V)SerializationUtils.deserialize(value);
		}
		log.info("-------------------------------redis中没有数据     --------------------------------------------------");
		return null;
	}
	
	public V put(K key, V value) throws CacheException {
		byte[] k = getKey(key);
		byte[] v = SerializationUtils.serialize(value);
		jedisUtil.set(k, v);
		jedisUtil.expire(k, 600);
		return value;
	}

	public V remove(K key) throws CacheException {
		byte[] k = getKey(key);
		byte[] value = jedisUtil.get(k);
		jedisUtil.del(k);
		if(value != null) {
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	public void clear() throws CacheException {
		
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<V> values() { 
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
