package com.redis.api.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisNodeService implements InitializingBean {
    @Autowired
    private RedisNodeConfig redisNodeConfig;

    private Jedis jedis;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedis = redisNodeConfig.getRedis();
    }

    public void set(String key,String value){
        jedis.set(key,value);
    }
}
