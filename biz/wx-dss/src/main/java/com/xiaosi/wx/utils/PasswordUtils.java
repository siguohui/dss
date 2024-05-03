package com.xiaosi.wx.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    public static void main(String[] args) {
        SignatureAlgorithm RSAalg = Jwts.SIG.RS512;
        //$2a$10$l5fxsiGWs1OJ5UMtkQJZROsNaer.P78GX8FCfxwsK4xCRJOW9RHZC
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
