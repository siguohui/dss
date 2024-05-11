package com.xiaosi.wx.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantContextHolder {

    @Resource
    private TokenUtils tokenUtils;

    public Long getTenantId() {
        return tokenUtils.getTenantId();
    }
}
