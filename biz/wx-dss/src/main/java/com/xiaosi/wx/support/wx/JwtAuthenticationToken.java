package com.xiaosi.wx.support.wx;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String userId;

    public JwtAuthenticationToken(String userId) {
        super(Collections.emptyList());
        this.userId = userId;
        setAuthenticated(true);
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String userId) {
        super(authorities);
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }
}
