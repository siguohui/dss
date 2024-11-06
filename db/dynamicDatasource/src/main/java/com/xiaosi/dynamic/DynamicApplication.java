package com.xiaosi.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xiaosi.dynamic.mapper")
@SpringBootApplication
public class DynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicApplication.class,args);
    }
}
