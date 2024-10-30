package com.xaiaosi.i18n.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * i18n拦截器：用于从HTTP请求头中获取国际化参数，并设置当前线程的Locale。
 */
@Slf4j
public class MyI18nInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行调用。
     *
     * @param request  HttpServletRequest对象，提供对客户端请求信息的访问
     * @param response HttpServletResponse对象，提供对HTTP响应的控制
     * @param handler  被调用的处理器，通常是HandlerMethod
     * @return 如果返回false，则请求处理流程将被中断并返回给客户端；如果返回true，则继续执行后续流程
     * @throws Exception 抛出异常将中断请求处理流程
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 假设请求头中存储语言信息的键名为"Language"
        final String key = "Language";
        // 从请求头中获取语言信息
        String language = request.getHeader(key);
        // 使用自定义的字符串工具类判断语言信息是否非空
        if (StringUtils.isNotEmpty(language)) {
            // 假设语言信息格式为"zh_CN"或"en_US"，这里通过下划线分割获取语言和地区信息
            String[] languageParts = language.split("_");
            // 确保分割后有两个部分
            if (languageParts.length == 2) {
                // 创建Locale对象
                Locale locale = new Locale(languageParts[0], languageParts[1]);
                // 设置当前线程的Locale
                LocaleContextHolder.setLocale(locale);
            } else {
                // 如果格式不正确，可以记录日志或进行其他处理
                log.warn("Invalid language format: {}", language);
            }
        }
        // 继续执行后续流程
        return true;
    }

    /**
     * 在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * 通常用于处理一些需要在请求处理之后、视图渲染之前执行的逻辑
     *
     * @param request   HttpServletRequest对象
     * @param response  HttpServletResponse对象
     * @param handler   被调用的处理器
     * @param modelAndView 如果处理器方法的返回值是ModelAndView类型，则为该方法的返回值，否则为null
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 可以在这里添加处理请求处理之后、视图渲染之前的逻辑
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param request   HttpServletRequest对象
     * @param response  HttpServletResponse对象
     * @param handler   被调用的处理器
     * @param ex        如果在请求处理过程中发生异常，则为该异常，否则为null
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 可以在这里添加请求处理完毕后的清理逻辑，例如关闭资源等
    }
}
