package com.xiaosi.wx.config;

import com.xiaosi.wx.filter.JwtAuthenticationFilter;
import com.xiaosi.wx.handler.MenujSecurityMetadataSource;
import com.xiaosi.wx.handler.MenuAccessDecisionManager;
import com.xiaosi.wx.support.sms.SmsAuthenticationProvider;
import com.xiaosi.wx.support.sms.SmsLoginConfigurer2;
import com.xiaosi.wx.token.WxDssPersistentTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    MenujSecurityMetadataSource customSecurityMetadataSource;

    @Autowired
    MenuAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    private WxDssPersistentTokenRepositoryImpl persistentTokenRepository;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*return PasswordEncoderFactories.createDelegatingPasswordEncoder();*/
        return new BCryptPasswordEncoder();
    }

    // 引入自定义评估器
    /*@Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(HasPermissionEvaluator hasPermissionEvaluator) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(hasPermissionEvaluator);
        return handler;
    }*/

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                                 AuthenticationSuccessHandler successHandler,
                                                                 AuthenticationFailureHandler failureHandler,
                                                                 AuthenticationEntryPoint entryPoint,
                                                                 AccessDeniedHandler accessDeniedHandler,
                                                                 AuthorizationManager authorizationManager,
                                                                 LogoutSuccessHandler logoutSuccessHandler,
                                                                 @Qualifier("jwtSecurityContextRepository") SecurityContextRepository securityContextRepository) throws Exception {
        ApplicationContext applicationContext =
                http.getSharedObject(ApplicationContext.class);
        http
                .authorizeHttpRequests(r -> r
                      .requestMatchers(HttpMethod.POST, "/refresh").permitAll()
//                        .requestMatchers("/login").anonymous()
                        .anyRequest()
//                                .access("@ServiceImpl.hasPermission(request,authentication)")
                                .access(authorizationManager)
//                                                        .authenticated()
                                /*.access((authentication, object) -> {
                                    //表示请求的 URL 地址和数据库的地址是否匹配上了
                                    boolean isMatch = true;
                                    AntPathMatcher antPathMatcher = new AntPathMatcher();
                                    //获取当前请求的 URL 地址
                                    String requestURI = object.getRequest().getRequestURI();
                                    *//*List<MenuWithRoleVO> menuWithRole = menuService.getMenuWithRole();
                                    for (MenuWithRoleVO m : menuWithRole) {
                                        if (antPathMatcher.match(m.getUrl(), requestURI)) {
                                            isMatch = true;
                                            //说明找到了请求的地址了
                                            //这就是当前请求需要的角色
                                            List<Role> roles = m.getRoles();
                                            //获取当前登录用户的角色
                                            Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
                                            for (GrantedAuthority authority : authorities) {
                                                for (Role role : roles) {
                                                    if (authority.getAuthority().equals(role.getName())) {
                                                        //说明当前登录用户具备当前请求所需要的角色
                                                        return new AuthorizationDecision(true);
                                                    }
                                                }
                                            }
                                        }
                                    }*//*
                                    if (!isMatch) {
                                        //说明请求的 URL 地址和数据库的地址没有匹配上，对于这种请求，统一只要登录就能访问
                                        if (authentication.get() instanceof AnonymousAuthenticationToken) {
                                            return new AuthorizationDecision(false);
                                        } else {
                                            //说明用户已经认证了
                                            return new AuthorizationDecision(true);
                                        }
                                    }
                                    return new AuthorizationDecision(true);
                        })*/
                       /* .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                                fsi.setSecurityMetadataSource(customSecurityMetadataSource);
                                fsi.setAccessDecisionManager(urlAccessDecisionManager);
                                return fsi;
                            }
                        })*/
                )

                .formLogin(f -> f.successHandler(successHandler)
                                .failureHandler(failureHandler)
                                .securityContextRepository(securityContextRepository))
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // 开启默认的记住我功能
                .rememberMe(remember -> remember.rememberMeCookieName("rememberMe").tokenRepository(persistentTokenRepository))
                .securityContext(s-> s.requireExplicitSave(true).securityContextRepository(securityContextRepository))
                .cors(withDefaults())
                .exceptionHandling(e->e.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler))
                .headers(h-> h.frameOptions(withDefaults()).disable())
                // 配置退出登录
                .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler).deleteCookies("rememberMe"))
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);



        /*Field field = HttpSecurity.class.getDeclaredField("filterOrders");
        field.setAccessible(true);
        Object registration = field.get(http);
        Method method = registration.getClass().getDeclaredMethod("put",Class.class, int.class);
        method.setAccessible(true);
        method.invoke(registration, SmsAuthenticationFilter.class, 1901);

        SmsLoginConfigurer<HttpSecurity> configurer = new SmsLoginConfigurer<>("/code");
        configurer.successHandler(successHandler).failureHandler(failureHandler);
        http.apply(configurer);*/

        SmsLoginConfigurer2<HttpSecurity> smsLoginConfigurer2 = new SmsLoginConfigurer2<>();
        smsLoginConfigurer2.successHandler(successHandler).failureHandler(failureHandler);
        http.apply(smsLoginConfigurer2);
        http.authenticationProvider(new SmsAuthenticationProvider());
        return http.build();

    }

   /* @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        // 关联使用的密码编码器
        provider.setPasswordEncoder(passwordEncoder);
        // 将provider放置进 AuthenticationManager 中,包含进去
        ProviderManager providerManager = new ProviderManager(provider);

        return providerManager;
    }*/

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext =
                http.getSharedObject(ApplicationContext.class);
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new
                                                 ObjectPostProcessor<FilterSecurityInterceptor>() {
                                                     @Override
                                                     public <O extends FilterSecurityInterceptor> O
                                                     postProcess(O object) {
                                                         object.setSecurityMetadataSource(customSecurityMetadataSource);
                                                         return object;
                                                     }
                                                 });
        http.formLogin()
                .and()
                .csrf().disable();
    }*/

   /* @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(JwtTokenUtil.getKey());
        return new ImmutableJWKSet<>(jwkSet);
    }*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/index","/list");
    }

   /* @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }*/

    /*@Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
        return pm;
    }*/

   /* @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // DaoAuthenticationProvider 从自定义的 userDetailsService.loadUserByUsername 方法获取UserDetails
        authProvider.setUserDetailsService(userDetailsService());
        // 设置密码编辑器
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

    /*@Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {
        // 调用 JwtUserDetailService实例执行实际校验
        return username -> userDetailsService.loadUserByUsername(username);
    }*/
}
