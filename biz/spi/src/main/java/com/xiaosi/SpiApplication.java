package com.xiaosi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpiApplication.class, args);
        System.out.println(run.getBean("beanDemo"));
    }
}
