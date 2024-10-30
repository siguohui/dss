package com.xiaosi.webjars.ws.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * @Author: 凉白开不加冰
 * @Version: 0.0.1V
 * @Date: 2018/11/20
 * @Description: redis工具类
 **/
public class RedisTools {

    public static <T> List<T> hget(StringRedisTemplate stringRedisTemplate,String key,Class<T> clazz){
        List<Object> list = stringRedisTemplate.opsForHash().values(key);
        return JSON.parseArray(list.toString(), clazz);
    }

    public static <T> T hget(StringRedisTemplate stringRedisTemplate,String h,String key,Class<T> clazz){
        Object o = stringRedisTemplate.opsForHash().get(h,key);
        return JSON.parseObject(o.toString(), clazz);
    }

    public static void hset(StringRedisTemplate stringRedisTemplate,String h,String key,String value){
        stringRedisTemplate.opsForHash().put(h,key,value);
    }

    public static void expire(StringRedisTemplate stringRedisTemplate,String key,Integer time){
        stringRedisTemplate.expire(key,time, TimeUnit.SECONDS);
    }

    public static Boolean existsHash(StringRedisTemplate stringRedisTemplate,String h,String key){
        return stringRedisTemplate.opsForHash().hasKey(h,key);
    }

    public static Boolean existsValue(StringRedisTemplate stringRedisTemplate,String key){
        return stringRedisTemplate.hasKey(key);
    }

    public static Long hdel(StringRedisTemplate stringRedisTemplate,String h,String key){
        return stringRedisTemplate.opsForHash().delete(h,key);
    }

    public static void set(StringRedisTemplate stringRedisTemplate,String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
        expire(stringRedisTemplate,key,60*60);
    }

    public static <T> T get(StringRedisTemplate stringRedisTemplate,String key,Class<T> clazz){
        Object object = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(object.toString(), clazz);
    }
}
