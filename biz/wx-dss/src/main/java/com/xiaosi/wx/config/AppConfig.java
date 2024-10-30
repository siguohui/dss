package com.xiaosi.wx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

//SpringMVC只扫描Controller，子容器
@ComponentScan(value = "springMVC", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class AppConfig {
}
