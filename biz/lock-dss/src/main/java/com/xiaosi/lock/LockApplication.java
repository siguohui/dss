package com.xiaosi.lock;

import cn.shuibo.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSecurity
@SpringBootApplication
public class LockApplication {
    public static void main(String[] args) {
        SpringApplication.run(LockApplication.class,args);
    }
}
