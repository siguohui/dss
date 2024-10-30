package com.xiaosi.webjars.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
//                .password("{noop}123456") //对密码不加密
//                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .password(passwordEncoder().encode("123456")) //对密码不加密
                .roles("ADMIN")
                .build();

        UserDetails xiaosi = User.withUsername("xiaosi")
//                .password("{noop}123456") //对密码不加密
//                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .password(passwordEncoder().encode("123456")) //对密码不加密
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin,xiaosi);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        //前后端分离认证逻辑
        http.formLogin((formLogin) -> formLogin
//                        .loginPage("/login.html")
//                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/index")
//                        .failureUrl("/login.html")
//                .successHandler(new LoginSuccessHandler())
//                .failureHandler(new LoginFailureHandler())
                        .permitAll()
        );

        //对请求进行访问控制设置
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                //设置哪些路径可以直接访问，不需要认证
                .requestMatchers("/doLogin","/test").permitAll()  //不需要认证
//                .requestMatchers("/*/*.css", "/**.js","/images/**", "/webjars/**", "/favicon.ico")
//                .permitAll()

                .requestMatchers("/index").hasRole("ADMIN")  //需要user角色,底层会判断是否有ROLE_admin权限
//                .requestMatchers("/index2").hasRole("admin")
//                .requestMatchers("/user/**").hasAuthority("user:api") //需要user:api权限
//                .requestMatchers("/order/**").hasAuthority("order:api")
//                .requestMatchers("/css/**").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()  //其他路径的请求都需要认证，仅仅认证通过后就可以了，不会去进行鉴权
        );

        http.logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**","/js/**","/images/**","/lib/**","doc.html","/webjars/**", "/swagger-resources/**", "/v3/**","/favicon.ico");
    }
}
