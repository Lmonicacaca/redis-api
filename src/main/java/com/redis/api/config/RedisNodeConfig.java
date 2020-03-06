package com.redis.api.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Slf4j
@Component
public class RedisNodeConfig implements InitializingBean {
    @Value("${java.redis.pool.max-total:10}")
    private Integer redisMaxTotal;
    @Value("${java.redis.pool.min-idle}")
    private Integer redisMaxIdle;
    @Value("${java.redis.pool.min-idle}")
    private Integer redisMinIdle;
    @Value("${java.redis.sentinel.nodes}")
    private String redisSentinelNodes;
    @Value("${java.redis.sentinel.password}")
    private String redisSentinelPassword;
    @Value("${java.redis.sentinel.master-name}")
    private String redisSentinelMasterName;
    @Value("${java.redis.sentinel.pool.max-total}")
    private Integer redisSentinelMaxTotal;
    @Value("${java.redis.sentinel.pool.max-idle}")
    private Integer redisSentinelMaxIdle;
    @Value("${java.redis.sentinel.pool.min-idle}")
    private Integer redisSentinelMinIdle;


    private JedisSentinelPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        jedisPoolConfig.setMaxIdle(redisMaxIdle);
        jedisPoolConfig.setMinIdle(redisMinIdle);
        String[] hosts = redisSentinelNodes.split(",");
        Set<String> sentinels = new HashSet<>(Arrays.asList(hosts));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(redisSentinelMaxTotal);
        poolConfig.setMaxIdle(redisSentinelMaxIdle);
        poolConfig.setMinIdle(redisSentinelMinIdle);
        jedisPool = new JedisSentinelPool(redisSentinelMasterName, sentinels, jedisPoolConfig);
        if (isConnectSuccess()) {
            // 初始化成功
            log.info("++++++ Init Redis Sentinel Pool SUCCESS! ++++++");
        } else {
            // 初始化失败
            log.info("++++++ Init Redis Sentinel Pool FAILURE! ++++++");
        }
    }
    public boolean isConnectSuccess() {
        Jedis jedis = getRedis();
        String ping = jedis.ping();
        recycleRedis(jedis);
        return ping.equals("PONG");
    }
    /**
     * @return 连接池中的Jedis实例
     */
    public Jedis getRedis() {
        return jedisPool.getResource();
    }
    /**
     * 资源回收
     *
     * @param jedis jedis实例
     */
    public void recycleRedis(Jedis jedis) {
        jedis.close();
    }

    /**
     * 销毁Redis连接池
     */
    public void close() {
        if (null != jedisPool) {
            jedisPool.close();
        }
    }



}
