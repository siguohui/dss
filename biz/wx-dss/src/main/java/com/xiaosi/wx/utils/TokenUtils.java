package com.xiaosi.wx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.exception.CustomException;
import com.xiaosi.wx.vo.SysUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenUtils {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final RedisUtil redisUtil;

    public SecurityContext getSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        /*String refreshToken = getRefreshToken();
        if(StringUtils.isNotBlank(refreshToken)){
            Claims claimsJws = null;
            try {
                claimsJws = jwtUtil.parseClaim(refreshToken).getPayload();
            } catch (ExpiredJwtException e) {
                claimsJws = e.getClaims();
            } catch (Exception e) {
                throw new CustomException(e.getCause().getMessage());
            }
        }*/

        String authorization = getHeaderToken();
        if(StringUtils.isNotBlank(authorization)&& jwtUtil.checkToken(authorization)){
            SysUserDetails sysUserDetails = (SysUserDetails) redisUtil.get(Constant.AUTH_KEY_PREFIX + jwtUtil.getUserName(authorization));
            securityContext.setAuthentication(sysUserDetails.getAuthentication());
            return securityContext;
        }
        return securityContext;
    }

    public void saveToken(SecurityContext context){
        if(context != null){
            SysUser sysUser = (SysUser) context.getAuthentication().getPrincipal();
            String token = jwtUtil.createToken(sysUser.getUsername());
            redisUtil.setBySecond(Constant.AUTH_KEY_PREFIX + sysUser.getUsername(),
                    SysUserDetails.builder()
                            .sysUser(sysUser)
                            .authentication(context.getAuthentication())
                            .token(token)
                            .build()
                    , jwtUtil.getTokenParam().getTokenExpiredMs());
        }
    }

    public String getRefreshToken(){
        if((StrUtil.equalsIgnoreCase(request.getMethod(),"POST") && "/refresh".equals(request.getRequestURI()))) {
            return request.getHeader("Refresh");
        }
        return "";
    }

    public String getHeaderToken()  {
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/
        String token = request.getHeader("Authorization");
        if(StrUtil.isNotBlank(token)) {
            List<String> tokenList = StrUtil.splitTrim(token, StrUtil.SPACE);
            if(CollUtil.size(tokenList) == 2 && StrUtil.equals(tokenList.get(0), "Bearer")) {
                token = tokenList.get(1);
            }
        } else if((StrUtil.equalsIgnoreCase(request.getMethod(),"POST") && StrUtil.containsIgnoreCase(request.getHeader("Content-Type"),"application/x-www-form-urlencoded"))) {
            token = request.getParameter("access-token");
        }  else {
            token = request.getParameter("access-token");
        }
        return token;
    }

    public SysUserDetails getSysUserDetails() {
        return  (SysUserDetails) redisUtil.get(Constant.AUTH_KEY_PREFIX + SecurityUtils.getUername());
    }

    public String getRedisToken(){
        return Optional.ofNullable(getSysUserDetails()).map(m->m.getToken()).orElse("");
    }
}
