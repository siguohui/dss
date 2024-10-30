package com.xiaosi.spring;

import com.xiaosi.spring.entity.Blue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MySpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MySpringApplication.class, args);

//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.xiaosi.spring.entity");
        Blue blue = ctx.getBean(Blue.class);
        System.out.println(blue);
    }
}
