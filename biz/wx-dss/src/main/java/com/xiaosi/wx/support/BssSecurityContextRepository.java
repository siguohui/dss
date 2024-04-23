package com.xiaosi.wx.support;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BssSecurityContextRepository implements SecurityContextRepository {

    private static final String AUTHENTICATION_CACHE_KEY_PREFIX = "security:authentication:";

    private final RedisTemplate<String,Object> redisTemplate;
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader("Authorization");

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if(StringUtils.isBlank(authorization)){
            return securityContext;
        }

        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        Authentication authentication = (Authentication)operations.get(AUTHENTICATION_CACHE_KEY_PREFIX + authorization);

        if(authorization == null){
            return securityContext;
        }
        securityContext.setAuthentication(authentication);
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        String accessToken = UUID.randomUUID().toString();
        operations.set(AUTHENTICATION_CACHE_KEY_PREFIX+accessToken,context.getAuthentication(), Duration.ofMinutes(2));
        request.setAttribute("accessToken",accessToken);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)){
            return false;
        }

        return BooleanUtils.isTrue(this.redisTemplate.hasKey(AUTHENTICATION_CACHE_KEY_PREFIX + authorization));
    }
}
