package com.xiaosi.wx;

import com.xiaosi.wx.entity.SysUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WxApplication {
    public static void main( String[] args ) {
        ConfigurableApplicationContext run = SpringApplication.run(WxApplication.class, args);
        SysUser user = (SysUser) run.getBeanFactory().getBean("user");
        System.out.println(user);
    }
}
