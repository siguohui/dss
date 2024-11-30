package com.xiaosi.rd;

import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@MapperScan("com.xiaosi.rd.mapper")
@SpringBootApplication
public class RdApplication {

    @Resource
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(RdApplication.class,args);
    }
}
