package com.xiaosi.wx.support;

import com.xiaosi.wx.support.wx.JwtAuthenticationToken;
import com.xiaosi.wx.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtSecurityContextRepository implements SecurityContextRepository {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    private static final String AUTHENTICATION_CACHE_KEY_PREFIX = "security:authentication:";

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if(StringUtils.isBlank(authorization)){
            return securityContext;
        }

        try {
            Claims payload = jwtUtil.parsePayload(authorization);
            if(Objects.isNull(payload)){
                return securityContext;
            }
            JwtAuthenticationToken token = new JwtAuthenticationToken(AuthorityUtils.commaSeparatedStringToAuthorityList((String) payload.get("authorities")),payload.getSubject());
            securityContext.setAuthentication(token);
        } catch (Exception e) {
            return securityContext;
        }
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String token = jwtUtil.createToken(userDetails.getUsername(),String.join(",",AuthorityUtils.authorityListToSet(context.getAuthentication().getAuthorities())));
        request.setAttribute("accessToken",token);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authorization)){
            return false;
        }

        return jwtUtil.validateToken(authorization);
    }
}
