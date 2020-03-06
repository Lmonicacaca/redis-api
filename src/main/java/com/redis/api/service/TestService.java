package com.redis.api.service;

import com.alibaba.fastjson.JSONObject;
import com.redis.api.config.RedisLockService;
import com.redis.api.config.RedisNodeService;
import com.redis.api.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lilin
 * @date 2020-03-02
 */
@Service
@Slf4j
public class TestService{
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisLockService redisLockService;

    @Autowired
    private RedisNodeService redisNodeService;

    public void testRedisService() {
//        boolean hasKey = redisService.hasKey("mylist");
//        log.info(String.valueOf(hasKey));
//        redisService.rename("t1","t2");
//        //###########String类相关方法#################
//        redisService.append("t2","saf");
//        redisService.decrement("age",5L);
//        redisService.increment("age","5.9");
//        redisService.getAndSet("t1","hello");
//        redisService.set("t1","fa");
//        redisService.set("test","test");
//        Set<String> keys = redisService.keys("*");
//        log.info(JSONObject.toJSONString(keys));
//        List<String> list = new ArrayList<>();
//        list.add("t1");
//        list.add("t2");
//        List<String> mgetList = redisService.multiGet(list);
//        log.info(JSONObject.toJSONString(mgetList));
//        Map<String,String> map = new HashMap<>();
//        map.put("t1","fsa");
//        map.put("t2","dsa");
//        redisService.multiSet(map);
//        //不存在则新增，存在则什么都不做
//        boolean result = redisService.setIfAbsent("t3", "rew");
//        log.info(String.valueOf(result));
//        //存在则更新value
//        boolean ifPresent = redisService.setIfPresent("t9", "er");
//        log.info(String.valueOf(ifPresent));
//        //获取value的字符串长度
//        long size = redisService.size("t1");
//        log.info(String.valueOf(size));
//
//        //###########list相关方法#################
//        redisService.lpush("mylist","haha");
//        List<String> list = new ArrayList<>();
//        list.add("xixi");
//        list.add("heihei");
//        redisService.lpushAll("mylist",list);
//        redisService.rpop("mylist");
//        log.info(String.valueOf(redisService.listSize("mylist")));
//        List mylist = redisService.listRange("mylist", 0, -1);
//        log.info(JSONObject.toJSONString(mylist));
//        Object mylist = redisService.index("mylist", 0);
//        log.info(JSONObject.toJSONString(mylist));
//        Object mylist = redisService.brpop("mylist", 0);
//        System.out.println(mylist.toString());
        //###########zset相关方法#################
//        redisService.addZset("myset","hh",3);
//        Set myset = redisService.zsetRange("myset", 0, -1);
//        log.info(JSONObject.toJSONString(myset));
//        long setSize = redisService.zSize("myset");
//        log.info(String.valueOf(setSize));
//        redisService.zsetRemove("myset","hh");


        redisNodeService.set("redis-sentinel","redis-sentinel");

    }

    public void testRedisLockService() throws InterruptedException {
        boolean lock = redisLockService.lock("testLock", "testLock", 1000000000, 0);
        try{
            if(lock){
                log.info("拿到锁，正在处理业务！");
                Thread.sleep(1000000000);
            }else {
                System.out.println("锁已被占用");
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }finally {
            redisLockService.releaseLock("testLock","testLock");
        }

    }

}
