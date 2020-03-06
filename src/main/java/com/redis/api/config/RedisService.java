package com.redis.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author lilin
 * @date 2020-03-02
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ListOperations listOperations;

    @Autowired
    private ZSetOperations zSetOperations;

    /**
     * 是否存在key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 模糊匹配key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }


    /**
     * 更换key的名字
     * @param oldKey
     * @param newKey
     */
    public void rename(String oldKey,String newKey){
        redisTemplate.rename(oldKey,newKey);
    }

    //########################String相关的操作########################
    /**
     * 添加数据
     * @param key
     * @param value
     */
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 添加数据并设置过期时间
     * @param key
     * @param value
     * @param time
     */
    public void set(String key,String value,long time){
        redisTemplate.opsForValue().set(key,value,time,TimeUnit.MICROSECONDS);
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除数据
     * @param key
     */
    public void del(String key){
        redisTemplate.delete(key);
    }
    /**
     * 在对应key的value后面追加值
     * @param key
     * @param value
     */
    public void append(String key,String value){
        redisTemplate.opsForValue().append(key,value);
    }

    /**
     * 使对应key的value减少num或者1
     * @param key
     * @param num
     */
    public void decrement(String key,Long num){
        if(num==null){
            redisTemplate.opsForValue().decrement(key);
        }else {
            redisTemplate.opsForValue().decrement(key,num);
        }

    }

    /**
     * 使对应key的value增加num或者1
     * @param key
     */
    public void increment(String key,Object num){
        if(num==null){
            redisTemplate.opsForValue().increment(key);
        }else if(String.valueOf(num).contains(".")){
            redisTemplate.opsForValue().increment(key,Double.parseDouble(String.valueOf(num)));
        }else {
            redisTemplate.opsForValue().increment(key,Long.parseLong(String.valueOf(num)));
        }

    }

    /**
     * 获取多个key对应的value的集合
     * @param collection
     * @return
     */
    public List<String> multiGet(Collection collection){
        return redisTemplate.opsForValue().multiGet(collection);
    }

    /**
     * 一次性设置多个值
     * @param map
     */
    public void multiSet(Map<String,String> map){
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 获取key对应的value的字符串长度
     * @param key
     * @return
     */
    public long size(String key){
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 添加数据，并且返回value
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(String key,String value){
        return redisTemplate.opsForValue().getAndSet(key,value);
    }

    /**
     * 当key不存在时添加数据，否则什么也不做
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key,String value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    /**
     * 当key存在时，更新key的value
     * @param key
     * @param value
     * @return
     */
    public boolean setIfPresent(String key,String value){
        return redisTemplate.opsForValue().setIfPresent(key,value);
    }

    //########################list集合相关的操作########################
    /**
     * 在集合头部添加元素
     * @param key
     * @param value
     */
    public void lpush(Object key, Object value){
        listOperations.leftPush(key,value);
    }

    /**
     * 集合头部添加若干元素
     * @param key
     * @param collection
     */
    public void lpushAll(Object key,Collection collection){
        listOperations.leftPushAll(key,collection);
    }
    /**
     * 移除集合尾部元素
     * @param key
     */
    public void rpop(Object key){
        listOperations.rightPop(key);
    }

    /**
     * 获取集合中某下标的数据
     * @param key
     * @param index
     * @return
     */
    public Object index(Object key,long index){
        return listOperations.index(key,index);
    }

    /**
     * 获取集合从起始位置-终止位置的所有元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List listRange(Object key,long start,long end){
        return listOperations.range(key,start,end);
    }

    /**
     * 返回集合的长度
     * @param key
     * @return
     */
    public long listSize(Object key){
        return listOperations.size(key);
    }

    /**
     * 阻塞的获取元素
     * @param key
     * @param time
     * @return
     */
    public Object brpop(Object key,long time){
        return listOperations.rightPop(key,time,TimeUnit.SECONDS);
    }

    //########################zset集合相关的操作########################
    /**
     * 添加元素
     * @param key
     * @param value
     * @param score
     */
    public void addZset(Object key,Object value,double score){
        zSetOperations.add(key,value,score);
    }

    public void incrementScore(Object key, Object value,double score){
        zSetOperations.incrementScore(key,value,score);
    }

    /**
     * 获取集合从起始位置-结束位置的所有元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set zsetRange(Object key,long start,long end){
        return zSetOperations.range(key,start,end);

    }

    /**
     * 获取集合的长度
     * @param key
     * @return
     */
    public long zSize(Object key){
        return zSetOperations.size(key);
    }

    /**
     * 移除元素
     * @param key
     * @param value
     */
    public void zsetRemove(Object key,Object value){
        zSetOperations.remove(key,value);
    }
}
