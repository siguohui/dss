package com.xiaosi.lock.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Payload<T> implements Serializable {

    /**
     * token id
     */
    private String id;

    /**
     * 用户信息（用户名、角色...）
     */
    private T userInfo;

    /**
     * 令牌过期时间
     */
    private Date expiration;
}
