package com.xiaosi.wx.support;

import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.utils.JwtUtil;
import com.xiaosi.wx.utils.RedisUtil;
import com.xiaosi.wx.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements SecurityContextRepository {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RedisTemplate<String,Object> redisTemplate;
    private final TokenUtils tokenUtils;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
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
        return tokenUtils.getSecurityContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
       tokenUtils.saveToken(context);
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
