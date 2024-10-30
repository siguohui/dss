package com.xaiaosi.i18n.config;

import com.xaiaosi.i18n.interceptor.MyI18nInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /*@Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("Accept-Language");
        registry.addInterceptor(interceptor);

        // 创建一个自定义的国际化拦截器实例
        MyI18nInterceptor myHandlerInterceptor = new MyI18nInterceptor();
        // 使用拦截器注册器注册自定义的国际化拦截器
        InterceptorRegistration loginRegistry = registry.addInterceptor(myHandlerInterceptor);
        // 设置需要拦截的路径模式，这里配置为拦截所有路径（"/**"）
        loginRegistry.addPathPatterns("/**");
    }
    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }

    /**
     * 添加拦截器到Spring MVC的拦截器链中
     *
     * @param registry 拦截器注册器，用于添加和配置拦截器
     */
}
