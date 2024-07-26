package com.xiaosi.stomp.model;

/**
 * @ClassName: UserInfo
 * @Description:
 * @Author: 88578
 * @Date: 2022/5/18 14:46
 */

import java.security.Principal;

/**
 * 重写DefaultHandshakeHandler
 * 重写DefaultHandshakeHandler的determineUser方法，给每个连接session注入principal用户信息。
 * @author dengsx
 * @create 2021/07/22
 **/
public class UserInfo implements Principal {
    private String userName;

    @Override
    public String getName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
