package com.xiaosi.gongzhonghao.modules.auth.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 18:47
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
