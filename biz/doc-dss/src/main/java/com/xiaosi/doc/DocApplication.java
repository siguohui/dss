package com.xiaosi.doc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xiaosi.doc.mapper")
@SpringBootApplication
public class DocApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocApplication.class,args);
    }
}
