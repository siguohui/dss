package com.xiaosi.wx.support.wx;

import com.xiaosi.wx.support.SmsToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

public class WxAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //验证码存储介质取出
        //手机号取
        //比对
        String mobile = (String) authentication.getPrincipal();
        String code = (String) authentication.getCredentials();

        if(StringUtils.equals("123456",code)) {
            return new SmsToken(mobile,code, Collections.emptyList());
        }
        throw new BadCredentialsException("验证码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsToken.class.isAssignableFrom(authentication);
    }
}
