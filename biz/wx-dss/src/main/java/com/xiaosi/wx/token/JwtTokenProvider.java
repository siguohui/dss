package com.xiaosi.wx.token;

import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.utils.ServletUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {
    @Resource
    private TokenParam tokenParam;
    private static SecretKey key = Jwts.SIG.HS256.key().build();
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * Generate token for user login.
     * @param authentication
     * @return return a token string.
     */
    public String createJwtToken(Authentication authentication) {
        //user name
        String username = ((SysUser) authentication.getPrincipal()).getUsername();
        return Jwts.builder()
                .subject(username)
                .expiration(expiration())
                .claims(initClaims(username))
                .issuedAt(new Date())
                .signWith(getSecretKey(),ALGORITHM) //SignatureAlgorithm.HS512
                .compact();
    }

    private Map<String,Object> initClaims(String username){
        Map<String, Object> claims = new HashMap<>();
        //"iss" (Issuer): 代表 JWT 的签发者。在这个字段中填入一个字符串，表示该 JWT 是由谁签发的。例如，可以填入你的应用程序的名称或标识符。
        claims.put("iss","jx");
        //"sub" (Subject): 代表 JWT 的主题，即该 JWT 所面向的用户。可以是用户的唯一标识符或者其他相关信息。
        claims.put("sub",username);
        //"exp" (Expiration Time): 代表 JWT 的过期时间。通常以 UNIX 时间戳表示，表示在这个时间之后该 JWT 将会过期。建议设定一个未来的时间点以保证 JWT 的有效性，比如一个小时、一天、一个月后的时间。
        claims.put("exp",expiration());
        //"aud" (Audience): 代表 JWT 的接收者。这个字段可以填入该 JWT 预期的接收者，可以是单个用户、一组用户、或者某个服务。
        claims.put("aud","internal use");
        //"iat" (Issued At): 代表 JWT 的签发时间。同样使用 UNIX 时间戳表示。
        claims.put("iat",new Date());
        //"jti" (JWT ID): JWT 的唯一标识符。这个字段可以用来标识 JWT 的唯一性，避免重放攻击等问题。
        claims.put("jti", UUID.randomUUID().toString());
        //"nbf" (Not Before): 代表 JWT 的生效时间。在这个时间之前 JWT 不会生效，通常也是一个 UNIX 时间戳。我这里不填，没这个需求
        return claims;
    }

    public Date expiration(){
        return new Date(System.currentTimeMillis()+tokenParam.getTokenExpiredMs());
    }
    /**
     * validate token eligible.
     * if Jwts can parse the token string and no throw any exception, then the token is eligible.
     * @param token a jws string.
     */
    public boolean validateToken(String token) {
        String VALIDATE_FAILED ="validate failed : ";
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (Exception ex) {
            //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, //IllegalArgumentException
            log.error(VALIDATE_FAILED + ex.getMessage());
            return false;
        }
    }

    public Jws<Claims> ParseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
    }

    public String getUsername(String token) {
        return ParseToken(token).getPayload().getSubject();
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(tokenParam.getJwtTokenSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameFromToken(){
        String token = ServletUtils.getRequest().getHeader(tokenParam.getTokenHeader());
        return getUsername(token);
    }
}
