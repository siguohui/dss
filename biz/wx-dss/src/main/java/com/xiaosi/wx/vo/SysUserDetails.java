package com.xiaosi.wx.vo;

import com.xiaosi.wx.entity.SysUser;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode
@AllArgsConstructor
public class SysUserDetails implements Serializable {

    private SysUser sysUser;
    private Authentication authentication;
    private String token;
    @Tolerate
    public SysUserDetails() {
    }
}
