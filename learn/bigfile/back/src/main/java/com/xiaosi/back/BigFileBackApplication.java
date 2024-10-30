package com.xiaosi.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xiaosi.back.mapper")
@SpringBootApplication
public class BigFileBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BigFileBackApplication.class,args);
    }
}
