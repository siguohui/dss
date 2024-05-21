package com.xiaosi.gongzhonghao.modules.auth.controller;

import com.xiaosi.gongzhonghao.common.Result;
import com.xiaosi.gongzhonghao.modules.auth.domain.LoginDTO;
import com.xiaosi.gongzhonghao.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 权限
 *
 * @author kcaco
 * @since 2022-08-19 18:46
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
