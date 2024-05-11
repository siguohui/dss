package com.xiaosi.wx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.vo.SysUserDetails;
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
    private final RedisUtil redisUtil;

    public SecurityContext getSecurityContext(HttpServletRequest request) {
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

        String authorization = getHeaderToken(request);
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
        HttpServletRequest request = ServletUtils.getRequest();
        if((StrUtil.equalsIgnoreCase(request.getMethod(),"POST") && "/refresh".equals(request.getRequestURI()))) {
            return request.getHeader("Refresh");
        }
        return "";
    }

    public String getHeaderToken(HttpServletRequest request)  {
//        HttpServletRequest request = ServletUtils.getRequest();
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/
        String token = request.getHeader(jwtUtil.getTokenParam().getTokenHeader());
        if(StrUtil.isNotBlank(token)) {
            List<String> tokenList = StrUtil.splitTrim(token, StrUtil.SPACE);
            if(CollUtil.size(tokenList) == 2 && StrUtil.equals(tokenList.get(0), jwtUtil.getTokenParam().getTokenPrefix())) {
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
        return (SysUserDetails) redisUtil.get(Constant.AUTH_KEY_PREFIX + SecurityUtils.getUername());
    }

    public SysUser getSysUser() {
        return getSysUserDetails().getSysUser();
    }

    public Long getUserId() {
        return getSysUser().getId();
    }

    public Long getTenantId() {
        return getSysUser().getTenantId();
    }

    public String getRedisToken(){
        return Optional.ofNullable(getSysUserDetails()).map(m->m.getToken()).orElse("");
    }
}
