package com.xiaosi.wx.support;

import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.support.wx.JwtAuthenticationToken;
import com.xiaosi.wx.utils.JwtUtil;
import com.xiaosi.wx.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements SecurityContextRepository {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if(StringUtils.isBlank(authorization)){
            return securityContext;
        }

        if(jwtUtil.checkToken(authorization)){
            ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
            Authentication authentication = (Authentication)operations.get(Constant.AUTH_KEY_PREFIX + jwtUtil.getUserName(authorization));

            if(authorization == null){
                return securityContext;
            }
            securityContext.setAuthentication(authentication);
            return securityContext;
        }

        /*try {
            Claims payload = jwtUtil.parsePayload(authorization);
            if(Objects.isNull(payload)){
                return securityContext;
            }

            JwtAuthenticationToken token = new JwtAuthenticationToken(AuthorityUtils.commaSeparatedStringToAuthorityList((String) payload.get("authorities")),payload.getSubject());
            securityContext.setAuthentication(token);
        } catch (Exception e) {
            return securityContext;
        }*/
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String token = jwtUtil.createToken(userDetails.getUsername(),String.join(",",AuthorityUtils.authorityListToSet(context.getAuthentication().getAuthorities())));

        ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
        operations.set(Constant.AUTH_KEY_PREFIX + userDetails.getUsername(),context.getAuthentication(), Duration.ofMinutes(10));
        redisUtil.setMinutes(Constant.TOKEN_KEY_PREFIX + userDetails.getUsername(),token, 10);
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
