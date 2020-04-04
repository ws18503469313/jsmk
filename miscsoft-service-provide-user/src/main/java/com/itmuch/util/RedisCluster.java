package com.itmuch.util;

import com.google.common.collect.Sets;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Set;
@Component
public class RedisCluster implements InitializingBean, ApplicationContextAware, FactoryBean<JedisCluster> {

    private JedisCluster jedisCluster;

    private ApplicationContext applicationContext;

    private static final String SPELT = ":";


//    private Integer timeout = 3000;
//
//    private Integer maxidle = 10;
//
//    private Integer maxTotal = 10;
//
//    private Integer minidle = 10;

    @Override
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisCluster.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            String [] values = this.applicationContext.getEnvironment().getProperty("redis.cluster.hostAndPort").split(SPELT);
            Set<HostAndPort> nodes = Sets.newHashSet(new HostAndPort(values[0], Integer.valueOf(values[1])));
//            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//            poolConfig.setMaxIdle(maxidle);
//            poolConfig.setMaxTotal(maxTotal);
//            poolConfig.setMinIdle(minidle);
//            poolConfig.setTestOnBorrow(true);
            //还是直接使用默认配置吧
            this.jedisCluster = new JedisCluster(nodes , 3000, 3000,
            5,  "password", new GenericObjectPoolConfig());
        }catch(Exception ex){
            throw new BeanCreationException(ex.getMessage());
        }

    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }
}
