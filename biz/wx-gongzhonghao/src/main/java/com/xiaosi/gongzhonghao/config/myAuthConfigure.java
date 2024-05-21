package com.xiaosi.gongzhonghao.config;

//import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class myAuthConfigure implements WebMvcConfigurer {

    // 注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
//        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
//            .addPathPatterns("/**/swagger-ui.html","/**/swagger-resources/**",
//                "/**/swagger-ui/index.html","/**/swagger/**","/**/v*/api-docs","/webjars/springfox-swagger-ui/**")
//            .excludePathPatterns("/auth/login");
//    }

    // 注册拦截器
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
            .addPathPatterns("/**")
            .excludePathPatterns("/push");
    }*/

    /**
     * 注册sa-token拦截器
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, resp, handler) -> {
            // 获取配置文件中的白名单路径
            List<String> ignoreUrls = Lists.newArrayList("/push");
            // 登录认证：除白名单路径外均需要登录认证
            SaRouter.match(Collections.singletonList("/**"), ignoreUrls, StpUtil::checkLogin);
        })).addPathPatterns("/**");
    }
*/
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor()).addPathPatterns("/**")
            .excludePathPatterns("/push","/captchaImage");
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }*/

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, resp, handler) -> {
            // 登录认证：除白名单路径外均需要登录认证
            SaRouter.match(Collections.singletonList("/**"), Lists.newArrayList("/push"), StpUtil::checkLogin);
        })).addPathPatterns("/**");
    }*/

}
