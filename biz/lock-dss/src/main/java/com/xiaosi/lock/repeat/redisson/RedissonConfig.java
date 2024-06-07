package com.xiaosi.lock.repeat.redisson;

import com.google.common.base.Joiner;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        String url = Joiner.on("").join("redis://", redisProperties.getHost(), ":", redisProperties.getPort());
        config.useSingleServer()
                // 使用与Spring Data Redis相同的地址
                .setAddress(url)
                // 如果有密码
                .setPassword(redisProperties.getPassword());
        // 其他配置参数
        //.setDatabase(0)
        //.setConnectionPoolSize(10)
        //.setConnectionMinimumIdleSize(2);
        // 创建RedissonClient实例
        return Redisson.create(config);
    }
}
