package com.xiaosi.wx.utils;

import com.xiaosi.wx.config.RsaKeyProperties;
import com.xiaosi.wx.exception.CustomException;
import com.xiaosi.wx.pojo.ResultEnum;
import io.jsonwebtoken.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Data
@Component
@ConfigurationProperties(prefix = "jjwt.token")
public class JwtUtil {

    @Resource
    private RsaKeyProperties rsaKeyProperties;

    private String secretKey;

    private long expireTime;
    /**
     * 创建JWT Token
     *
     * @param username
     * @return JWT Token
     */
    public String createToken(String username,String authorities) {
        return Jwts.builder()
                .claims(initClaims(username, authorities))
                .expiration(expiration())
                .signWith(rsaKeyProperties.getPrivateRsaKey(),Jwts.SIG.RS512)
//                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
    }

    private Map<String,Object> initClaims(String username,String authorities){

       /* iss (issuer)：签发人
        sub (subject)：主题
        aud (audience)：受众
        exp (expiration time)：过期时间
        nbf (Not Before)：生效时间，在此之前是无效的
        iat (Issued At)：签发时间
        jti (JWT ID)：编号*/

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
        claims.put("authorities",authorities);
        return claims;
    }

    private Date expiration()
    {
        return new Date(System.currentTimeMillis() + Duration.ofMinutes(expireTime).toMillis());
    }

    public Jws<Claims> parseClaim(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(rsaKeyProperties.getPublicRsaKey())
                    .build()
                    .parseSignedClaims(token);
        }catch (ExpiredJwtException expiredJwtException) {
            throw new CustomException("TOKEN已过期");
        }
        catch (Exception e) {
            throw new CustomException(e.getCause().getMessage());
        }
    }

    public JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    public Claims parsePayload(String token) {
        try {
            return parseClaim(token).getPayload();
        } catch (Exception e) {
            throw new CustomException(e.getCause().getMessage());
        }
    }

    public String getUserName(String token){
        String username;
        try
        {
            username = Optional.ofNullable(parsePayload(token)).map(m->m.getSubject()).orElse("");
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 获取JWT Token的过期时间
     *
     * @param token JWT Token
     * @return 过期时间
     */
    public Date getExpiration(String token) {
        try {
            return parsePayload(token).getExpiration();
        } catch (Exception e) {
            throw new CustomException(e.getCause().getMessage());
        }
    }

    /**
     * 判断token是否有过期
     * @param token 需要被验证的token
     * @return true/false
     */
    private boolean isTokenExpired(String token) {
        try {
           return getExpiration(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        return validateToken(token,null);
    }

    /**
     * 验证token是否有效
     * @param token 需要被验证的token
     * @param userDetails true/false
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails) {
        if(Objects.isNull(userDetails)){
            return isTokenExpired(token);
        }
        return getUserName(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     * @param token 需要被验证的token
     * @return true/false
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token 需要被刷新的token
     * @return 刷新后的token
     */
    public String refreshToken(String token){
        Claims claims = parsePayload(token);
        Map<String, Object> initClaims = initClaims((String) claims.get("username"),(String) claims.get("authorities"));
        initClaims.put("iat",new Date());
        return Jwts.builder()
                .claims(initClaims)
                .expiration(expiration())
                .signWith(rsaKeyProperties.getPrivateRsaKey(),Jwts.SIG.RS512)
                .compact();
    }

    public final static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token;
    }

    /**
     * 解析JWT Token
     */
   /* public Map<String, Object> parseToken(String token) {
        // 2. 解析JWT Token
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 3. 将Claims里的内容转换成Map
        Map<String, Object> payload = new HashMap<>(claims);
        payload.remove("exp");
        payload.remove("iat");
        payload.remove("iss");
        payload.remove("aud");
        payload.remove("nbf");
        payload.remove("sub");
        payload.remove("jti");

        return payload;
    }*/

}
