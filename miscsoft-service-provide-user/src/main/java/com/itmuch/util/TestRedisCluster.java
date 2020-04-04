package com.itmuch.util;

import com.google.common.collect.Sets;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import sun.net.www.content.text.Generic;

import java.util.Map;
import java.util.Set;

public class TestRedisCluster {

    public static void main(String args[]) throws Exception{
//        Jedis jedis = new Jedis("62.234.95.108", 6379);
//        jedis.auth("polunzi");
//        jedis.set("wang", "shuai");
//        System.out.println(jedis.get("wang"));
        Set<HostAndPort> nodes = Sets.newHashSet(new HostAndPort("62.234.95.108", 30001));

        JedisCluster cluster = new JedisCluster(nodes , 3000, 3000,
                5,  "wsa1583505", new GenericObjectPoolConfig());
        for (Map.Entry<String, JedisPool> pool : cluster.getClusterNodes().entrySet()){
            pool.getValue().getResource().auth("wsa1583505");
        }
        System.out.println(cluster.set("hello", "jedis-cluster"));
        System.out.println(cluster.get("hello"));
    }
}
