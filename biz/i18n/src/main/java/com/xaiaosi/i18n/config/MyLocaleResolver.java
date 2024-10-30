package com.xaiaosi.i18n.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import java.util.Locale;

//国际化解析器
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求中的语言参数
        String language = httpServletRequest.getParameter("Accept-Language");
        //默认的；如果没有就使用默认的
        Locale locale = Locale.getDefault();
        //如果请求的了携带了国际化的参数
        if(!StringUtils.isEmpty(language)){
            //zh_CN
            String[] split = language.split("_");
            //国家、地区
            locale = new Locale(split[0], split[1]);

        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
