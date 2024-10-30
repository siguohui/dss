package com.xiaosi.stomp.thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 功能描述：在该方法中配直两个用户，一个用户名是 admin ,密码是 123456，具备两个角色 ADMIN 和 USER，另一个用户名是 wi-gang ，密码是 123456 ，具备一个角色 USER
     * @author wi-gang
     * @date 2022/1/23 11:28 下午
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq").roles("ADMIN")
                .and()
                .withUser("wi-gang").password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq").roles("USER");
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        //使用默认加密方式bcrypt对密码进行加密，添加用户信息
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("xiaosi")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
//                .password("{noop}123456") //对密码不加密
//                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                .password(passwordEncoder().encode("123456")) //对密码不加密
                .roles("ADMIN")
                .build();
        // 返回一个UserDetailsService的实现类InMemoryUserDetailsManager，从类名可以看出来是基于内存的
        return new InMemoryUserDetailsManager(user, admin);
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }*/

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //表单提交
        // /user/login接口不是我们程序员定义的接口
        http.formLogin((formLogin) -> formLogin
                .loginPage("/login.html") //指定自定义登录页面地址
                .loginProcessingUrl("/user/login")//登录访问路径：前台界面提交表单之后跳转到这个路径进行UserDetailsService的验证，必须和表单提交接口一样
                .defaultSuccessUrl("/admin/demo")//认证成功之后跳转的路径
        );

        //对请求进行访问控制设置
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                //设置哪些路径可以直接访问，不需要认证
                .requestMatchers("/login.html","/user/login").permitAll()
                .anyRequest().authenticated() //其他路径的请求都需要认证
        );

        //关闭跨站点请求伪造csrf防护
        http.csrf((csrf) -> csrf.disable());

        return http.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //前后端分离认证逻辑
        http.formLogin((formLogin) -> formLogin
                .loginProcessingUrl("/login")
//                .successHandler(new LoginSuccessHandler())
//                .failureHandler(new LoginFailureHandler())
                        .permitAll()
        );

        //对请求进行访问控制设置
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                //设置哪些路径可以直接访问，不需要认证
                .requestMatchers("/login").permitAll()  //不需要认证
                .requestMatchers("/index").hasRole("user")  //需要user角色,底层会判断是否有ROLE_admin权限
                .requestMatchers("/index2").hasRole("admin")
                .requestMatchers("/user/**").hasAuthority("user:api") //需要user:api权限
                .requestMatchers("/order/**").hasAuthority("order:api")
                .requestMatchers("/css/**").permitAll()
                .anyRequest().authenticated()  //其他路径的请求都需要认证，仅仅认证通过后就可以了，不会去进行鉴权
        );

        http.logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**","doc.html","/webjars/**", "/swagger-resources/**", "/v3/**","/favicon.ico");
    }
}
