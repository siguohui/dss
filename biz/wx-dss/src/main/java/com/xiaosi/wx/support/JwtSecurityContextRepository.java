package com.xiaosi.wx.support;

import com.xiaosi.wx.support.wx.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtSecurityContextRepository implements SecurityContextRepository {

    /**
     * SECRET 是签名密钥，只生成一次即可，生成方法：
     * Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
     * String secretString = Encoders.BASE64.encode(key.getEncoded()); # 本文使用 BASE64 编码
     * */
    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
//    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        if(StringUtils.isBlank(authorization)){
            return securityContext;
        }

        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(authorization);
        } catch (JwtException e) {
            return securityContext;
        }
        Claims payload = claimsJws.getPayload();

        JwtAuthenticationToken token = new JwtAuthenticationToken(AuthorityUtils.commaSeparatedStringToAuthorityList((String) payload.get("authorities")),(String) payload.get("userId"));

        if(authorization == null){
            return securityContext;
        }
        securityContext.setAuthentication(token);
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();

        String token = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(10).toMillis()))
                .claim("userId", userDetails.getUsername())
                .claim("authorities", String.join(",",AuthorityUtils.authorityListToSet(context.getAuthentication().getAuthorities())))
                .signWith(key).compact();
        request.setAttribute("accessToken",token);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authorization)){
            return false;
        }

        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(authorization);
        } catch (JwtException e) {
            return false;
        }
        Claims payload = claimsJws.getPayload();

        return true;
    }
}
