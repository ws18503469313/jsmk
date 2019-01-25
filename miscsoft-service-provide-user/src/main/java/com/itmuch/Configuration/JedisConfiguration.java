package com.itmuch.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
public class JedisConfiguration {

	private static final Logger log = LoggerFactory.getLogger(JedisConfiguration.class);

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.block-when-exhausted}")
	private boolean blockWhenExhausted;

	@Bean
	public JedisPool redisPoolFactory()  throws Exception{
		
	    log.info("redis地址：" + host + ":" + port);
	    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxIdle(maxIdle);
	    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
	    // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
	    jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
	    // 是否启用pool的jmx管理功能, 默认true
	    jedisPoolConfig.setJmxEnabled(true);
	    JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
	    Jedis test = jedisPool.getResource();
	    log.info("----------------------------JedisPool注入成功！！"+test+"-----------------------------");
	    test.close();
	    return jedisPool;
	}
}