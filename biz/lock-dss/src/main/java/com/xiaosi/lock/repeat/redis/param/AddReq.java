package com.xiaosi.lock.repeat.redis.param;

import com.xiaosi.lock.repeat.redis.annotation.RequestKeyParam;
import lombok.Data;

import java.util.List;

@Data
public class AddReq {

    /**
     * 用户名称
     */
    @RequestKeyParam
    private String userName;

    /**
     * 用户手机号
     */
    @RequestKeyParam
    private String userPhone;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;
}
