package com.xaiaosi.i18n.controller;

import com.xaiaosi.i18n.i18n.DynamicMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    MessageSource messageSource;
    @Autowired
    private DynamicMessageSource dynamicMessageSource;
    @GetMapping("/hello")
    public String hello() {
        return messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/test/{code}")
    public String test(@PathVariable("code") String code) {
//        String code = "dynamic.hello";

        String message = dynamicMessageSource.getMessage(code, new String[]{"索码理"}, LocaleContextHolder.getLocale());
        System.out.println(message);
        System.out.println();

        //占位符替换
        code = "dynamic.welcome";
        message = dynamicMessageSource.getMessage(code, new String[]{"索码理"}, LocaleContextHolder.getLocale());
        System.out.println(message);
        System.out.println();

        message = dynamicMessageSource.getMessage(code, new String[]{"suncodernote"}, LocaleContextHolder.getLocale());
        System.out.println(message);

        return messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale());
    }
}
