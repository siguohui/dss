package com.xiaosi.wx.support.sms;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SmsLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractAuthenticationFilterConfigurer<H,SmsLoginConfigurer<H>,SmsAuthenticationFilter> {
    public SmsLoginConfigurer(String defaultLoginProcessingUrl) {
        super(new SmsAuthenticationFilter(defaultLoginProcessingUrl), defaultLoginProcessingUrl);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }
}
