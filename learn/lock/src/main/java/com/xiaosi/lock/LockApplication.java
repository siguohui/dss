package com.xiaosi.lock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@MapperScan("com.xiaosi.lock.mapper")
@SpringBootApplication
public class LockApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(LockApplication.class, args);
    }
}
