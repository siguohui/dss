package com.xiaosi.wx.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author jx
 * @Date 2023/11/27
 * @Version 1.0
 * @Description: jwt工具类，JSON WEB TOKEN，分别由：Header（头部）、Payload（负载）、Signature（签名）组成
 */

public class JwtTokenUtil {

    //如果您不想考虑位长度要求，或者只是想让您的生活更轻松，
    //JWT 提供了方便的构建器类，可以为任何给定的密钥生成足够安全的密钥
//    SecretKey secretKey = Jwts.SIG.HS256.key().build();
    //自定义秘钥也可以 , 不同的算法需要的秘钥长度不同
    //SecretKey key = Keys.hmacShaKeyFor("AWEQWEWE23123156qqqq465wqeqweqweqwewqeqweqweqweq4".getBytes(StandardCharsets.UTF_8)); // 你的密钥

    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    private static SecretKey key = Jwts.SIG.HS256.key().build();
    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private final static String SECRET = "secretKey";
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private  long expireTime = 10;

    /**
     * 1.从本地文件读取秘钥
     * 2.工程.yml中配置salt
     * 3.最终生成jwt秘钥，秘钥组成：MD5（1+2）
     */
//    private SecretKey secret = Keys.hmacShaKeyFor(MD5Util.generatorSecret(FileProcessUtil.readLocalFileForSecret()+salt).getBytes());


    /**
     * 初始化负载内数据
     * @param username 用户名
     * @return 负载集合
     */
    private Map<String,Object> initClaims(String username){
        Map<String, Object> claims = new HashMap<>();
        //"iss" (Issuer): 代表 JWT 的签发者。在这个字段中填入一个字符串，表示该 JWT 是由谁签发的。例如，可以填入你的应用程序的名称或标识符。
        claims.put("iss","jx");
        //"sub" (Subject): 代表 JWT 的主题，即该 JWT 所面向的用户。可以是用户的唯一标识符或者其他相关信息。
        claims.put("sub",username);
        //"exp" (Expiration Time): 代表 JWT 的过期时间。通常以 UNIX 时间戳表示，表示在这个时间之后该 JWT 将会过期。建议设定一个未来的时间点以保证 JWT 的有效性，比如一个小时、一天、一个月后的时间。
        claims.put("exp",generatorExpirationDate());
        //"aud" (Audience): 代表 JWT 的接收者。这个字段可以填入该 JWT 预期的接收者，可以是单个用户、一组用户、或者某个服务。
        claims.put("aud","internal use");
        //"iat" (Issued At): 代表 JWT 的签发时间。同样使用 UNIX 时间戳表示。
        claims.put("iat",new Date());
        //"jti" (JWT ID): JWT 的唯一标识符。这个字段可以用来标识 JWT 的唯一性，避免重放攻击等问题。
        claims.put("jti",UUID.randomUUID().toString());
        //"nbf" (Not Before): 代表 JWT 的生效时间。在这个时间之前 JWT 不会生效，通常也是一个 UNIX 时间戳。我这里不填，没这个需求
        return claims;
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetails 用户信息
     * @return token
     */
    public String generatorToken(UserDetails userDetails)
    {
        Map<String, Object> claims = initClaims(userDetails.getUsername());
        return generatorToken(claims);
    }

    /**
     * 根据负载生成JWT token
     * @param claims 负载
     * @return token
     */
    private String generatorToken(Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims)
                .signWith(key, ALGORITHM)
                .compact();
    }

     /*return Jwts.builder()
             // 设置头部信息header
             .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
    // 设置自定义负载信息payload
                .claim("username", username)
    // 令牌ID
                .id(uuid)
    // 过期日期
                .expiration(exprireDate)
    // 签发时间
                .issuedAt(new Date())
            // 主题
            .subject(SUBJECT)
    // 签发者
                .issuer(JWT_ISS)
    // 签名
                .signWith(KEY, ALGORITHM)
                .compact();*/

    /**
     * 生成失效时间，以秒为单位
     *
     * @return 预计失效时间
     */
    private Date generatorExpirationDate()
    {
        //预计失效时间为：token生成时间+预设期间
        return new Date(System.currentTimeMillis() + expireTime * 1000);
    }

    /**
     * 从Token中获取用户名
     * @param token token
     * @return 用户名
     */
    public String getUserNameFromToken(String token){
        String username;
        try
        {
            username = parsePayload(token).getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 解析token
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    public static JwsHeader parseHeader(String token) {
        return parseClaim(token).getHeader();
    }

    private Claims parsePayload(String token)
    {
        return parseClaim(token)
                .getPayload();
    }

    /**
     * 验证token是否有效
     * @param token 需要被验证的token
     * @param userDetails true/false
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        return getUserNameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否有过期
     * @param token 需要被验证的token
     * @return true/false
     */
    private boolean isTokenExpired(String token)
    {
        //判断预设时间是否在当前时间之前，如果在当前时间之前，就表示过期了，会返回true
        return getExpiredDateFromToken(token).before(new Date());
    }

    /**
     * 从token中获取预设的过期时间
     * @param token token
     * @return 预设的过期时间
     */
    private Date getExpiredDateFromToken(String token)
    {
        return parsePayload(token).getExpiration();
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
        Map<String, Object> initClaims = initClaims(claims.getSubject());
        initClaims.put("iat",new Date());
        return generatorToken(initClaims);
    }
}

