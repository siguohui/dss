package com.xiaosi.wx.support.sms;

import com.xiaosi.wx.support.SmsToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Collections;

public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected SmsAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String mobile = request.getParameter("mobile");
        String code = request.getParameter("code");

        SmsToken smsToken = new SmsToken(mobile,code, Collections.emptyList());
        smsToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(smsToken);
    }
}
