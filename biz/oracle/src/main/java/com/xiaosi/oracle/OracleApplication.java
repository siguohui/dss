package com.xiaosi.oracle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xiaosi.oracle.mapper")
@SpringBootApplication
public class OracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OracleApplication.class,args);
    }
}
