package com.xiaosi.wx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.wx.common.Constant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenUtils {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final RedisUtil redisUtil;
    /*private final RedisTemplate<String,Object> redisTemplate;*/

    public String getToken()  {
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/
        String token = request.getHeader("Authorization");
        if(StrUtil.isNotBlank(token)) {
            List<String> tokenList = StrUtil.splitTrim(token, StrUtil.SPACE);
            if(CollUtil.size(tokenList) == 2 && StrUtil.equals(tokenList.get(0), "Bearer")) {
                token = tokenList.get(1);
            }
        } else if((StrUtil.equalsIgnoreCase(request.getMethod(),"POST") && StrUtil.containsIgnoreCase(request.getHeader("Content-Type"),"application/x-www-form-urlencoded"))) {
            token = request.getParameter("access-token");
        }
        return token;
    }

    public SecurityContext getSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        String authorization = getToken();
        if(StringUtils.isNotBlank(authorization)&& jwtUtil.checkToken(authorization)){
            /*ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();*/
            Authentication authentication = (Authentication)redisUtil.get(Constant.AUTH_KEY_PREFIX + jwtUtil.getUserName(authorization));
            securityContext.setAuthentication(authentication);
            return securityContext;
        }
        return securityContext;
    }

    public void saveToken(SecurityContext context){
        if(context != null){
            UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
            String token = jwtUtil.createToken(userDetails.getUsername(),String.join(",", AuthorityUtils.authorityListToSet(context.getAuthentication().getAuthorities())));
            /*ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();*/
            redisUtil.setBySecond(Constant.AUTH_KEY_PREFIX + userDetails.getUsername(),context.getAuthentication(), jwtUtil.getExpireTime());
            redisUtil.setBySecond(Constant.TOKEN_KEY_PREFIX + userDetails.getUsername(),token, jwtUtil.getExpireTime());
        }
    }
}
