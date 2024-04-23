package com.xiaosi.wx.config;

import com.xiaosi.wx.support.sms.SmsAuthenticationProvider;
import com.xiaosi.wx.support.sms.SmsLoginConfigurer2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                                 AuthenticationSuccessHandler successHandler,
                                                                 AuthenticationFailureHandler failureHandler,
                                                                 AuthenticationEntryPoint entryPoint,
                                                                 @Qualifier("bssSecurityContextRepository") SecurityContextRepository securityContextRepository) throws Exception {
         http
                .authorizeHttpRequests(r -> r
//                        .requestMatchers("/index").permitAll()
                        .anyRequest().authenticated())
                .formLogin(f -> f.successHandler(successHandler)
                                .failureHandler(failureHandler)
                                .securityContextRepository(securityContextRepository))
                        .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .securityContext(s-> s.requireExplicitSave(true).securityContextRepository(securityContextRepository))
                .cors(withDefaults())
                .exceptionHandling(e->e.authenticationEntryPoint(entryPoint))
                .headers(h-> h.frameOptions(withDefaults()).disable())
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/index","/list");
    }

    /*@Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
        return pm;
    }*/
}
