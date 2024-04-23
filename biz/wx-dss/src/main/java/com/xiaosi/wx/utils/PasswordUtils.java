package com.xiaosi.wx.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    public static void main(String[] args) {
        //$2a$10$l5fxsiGWs1OJ5UMtkQJZROsNaer.P78GX8FCfxwsK4xCRJOW9RHZC
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
