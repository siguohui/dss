package com.xiaosi.gongzhonghao.modules.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.gongzhonghao.common.Result;
import com.xiaosi.gongzhonghao.config.AuthConfig;
import com.xiaosi.gongzhonghao.modules.auth.domain.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * (SysUser)表服务实现类
 *
 * @author kcaco
 * @since 2022-08-19 19:00:53
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthConfig authConfig;

    /**
     * 登录接口
     *
     * @param loginDTO
     * @return
     */
    public Result login(LoginDTO loginDTO) {
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();

        if (StrUtil.hasBlank(userName, password)) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (StrUtil.hasBlank(authConfig.getUsername(), authConfig.getPassword())) {
            throw new RuntimeException("请配置登录账号信息！");
        }

        if (StrUtil.equals(userName, authConfig.getUsername()) && StrUtil.equals(password, authConfig.getPassword())) {
            // 登录并返回token
            StpUtil.login(userName);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Result.success(tokenInfo.tokenValue);
        }
        throw new RuntimeException("登录失败!");
    }
}

