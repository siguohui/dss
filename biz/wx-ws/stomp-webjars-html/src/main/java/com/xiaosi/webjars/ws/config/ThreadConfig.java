package com.xiaosi.webjars.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
//@EnableAsync   //开启异步执行
public class ThreadConfig {
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        //设置线程的名称
        //ThreadFactory springThreadFactory  = new CustomizableThreadFactory("springThread-pool-"+ UUID.randomUUID());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int i = Runtime.getRuntime().availableProcessors();//获取到服务器的cpu内核
        System.out.println("服务器的cpu内核是"+i);
        executor.setCorePoolSize(5);//核心池大小
        executor.setMaxPoolSize(100);//最大线程数
        executor.setQueueCapacity(1000);//队列程度
        executor.setKeepAliveSeconds(1000);//线程空闲时间
        //executor.setThreadNamePrefix("tsak-asyn");//线程前缀名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//配置拒绝策略
        return executor;
    }
}
