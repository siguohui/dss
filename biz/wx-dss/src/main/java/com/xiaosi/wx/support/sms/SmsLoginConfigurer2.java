package com.xiaosi.wx.support.sms;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class SmsLoginConfigurer2<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<SmsLoginConfigurer2<B>, B> {

    private AuthenticationSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    @Override
    public void configure(B builder) {
        SmsAuthenticationFilter smsFilter = new SmsAuthenticationFilter("/code");
        smsFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsFilter.setAuthenticationSuccessHandler(successHandler);
        smsFilter.setAuthenticationFailureHandler(failureHandler);
        smsFilter.setAuthenticationDetailsSource(new WebAuthenticationDetailsSource());
        builder.addFilterAfter(smsFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public final SmsLoginConfigurer2<B> successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public final SmsLoginConfigurer2<B> failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }
}
