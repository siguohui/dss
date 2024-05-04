package com.xiaosi.wx.model;

import com.baomidou.mybatisplus.annotation.*;
import com.xiaosi.wx.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@TableName(value = "sys_user",autoResultMap = true)
@Schema(description = "用户")
@Accessors(chain = true)
public class SysUser extends BaseEntity implements UserDetails {

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户名")
    @TableField(value = "username")
    @Getter(AccessLevel.NONE)
    private String username;

    @Override
    public String getUsername() {
        return this.username;
    }

    /** 密码 **/
    @Schema(description = "密码")
    @TableField(value = "password")
    @Getter(AccessLevel.NONE)
    private String password;

    @Override
    public String getPassword() {
        return this.password;
    }

    /** 开启 **/
    @Schema(description = "开启或者关闭")
    @TableField(value = "enabled")
    @Getter(AccessLevel.NONE)
    private boolean enabled;

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /** 账号过期 **/
    @Schema(description = "账号过期")
    @TableField(value = "account_non_expired")
    @Getter(AccessLevel.NONE)
    private boolean accountNonExpired;

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /** 账号锁定 **/
    @Schema(description = "账号锁定")
    @TableField(value = "account_non_locked")
    @Getter(AccessLevel.NONE)
    private boolean accountNonLocked;

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /** 凭证过期 **/
    @Schema(description = "凭证过期")
    @TableField(value = "credentials_non_expired")
    @Getter(AccessLevel.NONE)
    private boolean credentialsNonExpired;

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @TableField(exist = false)
    @Getter(AccessLevel.NONE)
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /** 用户昵称 **/
    @Schema(description = "用户昵称")
    @TableField(value = "nickname")
    private String nickName;

    /** 性别：0、女 1、男 **/
    @Schema(description = "性别：0、女 1、男")
    @TableField(value = "sex")
    private int sex;

    /** 手机号 **/
    @Schema(description = "手机号")
    @TableField(value = "mobile")
    private String mobile;

    /** 邮箱 **/
    @Schema(description = "邮箱")
    @TableField(value = "email")
    private String email;

    /** 部门ID **/
    @Schema(description = "部门ID")
    @TableField(value = "dept_id")
    private Long deptId;

    /** 部门 **/
//    @Schema(description = "部门")
//    @TableField(exist = false)
//    private Dept dept;
//
//    @TableField(exist = false)
//    private List<Role> roles;
//
//    @Schema(hidden = true)
//    @TableField(exist = false)
//    private List<Perm> perms;
}
