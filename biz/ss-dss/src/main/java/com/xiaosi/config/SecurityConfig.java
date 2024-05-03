package com.xiaosi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {


    /*@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(HttpMethod.GET,"/articles/feed").authenticated()
                                .requestMatchers(HttpMethod.POST, "/users",  "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/articles/**", "/profiles/**", "/tags").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }*/

    @Bean
    UserDetailsService us1(){
        /**
         * AuthenticationProvider#retrieveUser  获取user 会调用 UserDetailsService#loadUserByUsername
         */
        // 配置UserDetailsService  返回UserDetails
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(User.builder().username("cc").password("{noop}123").roles("admin").build());
        return userDetailsService;
    }

    @Bean
    UserDetailsService us2(){
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(User.builder().username("hh").password("{noop}123").roles("user").build());
        return userDetailsService;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider provider01 = new DaoAuthenticationProvider();
        //设置用户信息处理器
        provider01.setUserDetailsService(us1());
        //设置密码处理器
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        DaoAuthenticationProvider provider02 = new DaoAuthenticationProvider();
        provider02.setUserDetailsService(us2());

//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(phoneCodeLoginProvider);//自定义的
//        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider);//原来默认的
//        return authenticationManagerBuilder.build();




        ProviderManager providerManager = new ProviderManager(provider01,provider02);


        return providerManager;
    }


    //当使用WebSecurityCustomizer的时候，过滤器会忽略后面请求的路径，
    // 默认就不会走springSecurity的过滤器和重写的springSecurity的过滤器。但是还是会被自定义的过滤器所拦截。因此常用于静态页面等的存放。
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**");
    }

}
