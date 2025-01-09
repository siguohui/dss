package com.xiaosi.back.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatiesPlusConfig {
    @Bean
    public MysqlInjector sqlInjector() {
        return new MysqlInjector();
    }
}
