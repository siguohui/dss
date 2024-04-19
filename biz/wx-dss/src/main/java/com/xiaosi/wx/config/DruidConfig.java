package com.xiaosi.wx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;

import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import com.alibaba.druid.util.Utils;
import jakarta.servlet.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * 去除广告
 * @author dgz
 */
//@Configuration
//@ConditionalOnWebApplication
//@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
//@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public FilterRegistrationBean removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

        final String filePath = "support/http/resources/js/common.js";

        //创建filter进行过滤
        Filter filter = new Filter() {
            @Override
            public void init(FilterConfig filterConfig){
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                chain.doFilter(request, response);
                // 重置缓冲区，响应头不会被重置
                response.resetBuffer();
                // 获取common.js
                String text = Utils.readFromResource(filePath);
                // 正则替换banner, 除去底部的广告信息
                text = text.replaceAll("<a.*?banner\"></a><br/>", "");
                text = text.replaceAll("powered.*?shrek.wang</a>", "");
                response.getWriter().write(text);
            }

            @Override
            public void destroy() {
            }
        };
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }

    /**
     * 配置Druid 监控启动页面
     *
     * @return servletRegistrationBean
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidStartViewServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");
        // 白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 黑名单
//        servletRegistrationBean.addInitParameter("deny", "");
        // 登录查看信息的账密，用于登录Druid监控后台
        servletRegistrationBean.addInitParameter("loginUsername", "ddz");
        servletRegistrationBean.addInitParameter("loginPassword", "ddz2023");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;
    }

    /**
     * Druid监控过滤器配置规则
     * ConditionalOnMissingBean 防止注册相同的bean
     *
     * @return filterFilterRegistrationBean
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new WebStatFilter());
        // 添加过滤规则
        filterFilterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息
        filterFilterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterFilterRegistrationBean;
    }

}