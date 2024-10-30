package com.xiaosi.mybatis.config;

import com.xiaosi.mybatis.mapper.MysqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatiesPlusConfig {
    @Bean
    public MysqlInjector sqlInjector(){
        return new MysqlInjector();
    }
}
