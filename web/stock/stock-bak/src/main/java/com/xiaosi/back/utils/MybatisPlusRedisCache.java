package com.xiaosi.back.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.util.DigestUtils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName : MybatisRedisCache
 * @Description : mybatis-plus结合redis自定义缓存管理
 * @author ZJ
 */
@Slf4j
public class MybatisPlusRedisCache implements Cache {


    /**
     * 注意，这里无法通过@Autowired等注解的方式注入bean,只能手动获取
     */
    private RedisUtils redisUtil;

    /**
     * 手动获取bean
     *
     * @return
     */
    private void getRedisUtil() {
        redisUtil = ApplicationContextUtils.getBean(RedisUtils.class);
    }


    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);


    private String id;

    public MybatisPlusRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("二级缓存id：{}",id);
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.info("存入缓存");

        if (redisUtil == null) {
            getRedisUtil();//获取bean
        }

        try {
            //将key加密后存入
            redisUtil.hset(this.id,this.MD5Encrypt(key),value);
        } catch (Exception e) {
            log.error("存入缓存失败！");
            e.printStackTrace();
        }


    }

    @Override
    public Object getObject(Object key) {
        log.info("获取缓存");

        if (redisUtil == null) {
            getRedisUtil();//获取bean
        }

        try {
            if (key != null) {
                return redisUtil.hget(this.id,this.MD5Encrypt(key));
            }
        } catch (Exception e) {
            log.error("获取缓存失败！");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object removeObject(Object key) {
        log.info("删除缓存");

        if (redisUtil == null) {
            getRedisUtil();//获取bean
        }


        try {
            if (key != null) {
                redisUtil.del(this.MD5Encrypt(key));
            }
        } catch (Exception e) {
            log.error("删除缓存失败！");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void clear() {
        log.info("清空缓存");
        if (redisUtil == null) {
            getRedisUtil();//获取bean
        }

        try {
            redisUtil.del(this.id);
        } catch (Exception e) {
            log.error("清空缓存失败！");
            e.printStackTrace();
        }

    }

    @Override
    public int getSize() {
        if (redisUtil == null) {
            getRedisUtil();//获取bean
        }
        Long size = (Long)redisUtil.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * MD5加密存储key,以节约内存
     */
    private String MD5Encrypt(Object key){
        String s = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        return s;
    }

}
