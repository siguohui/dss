package com.xiaosi.wx.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@TableName(value = "sys_user",autoResultMap = true)
@Schema(description = "用户")
public class SysUser implements Serializable, UserDetails {

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户名")
    @TableField(value = "name")
    private String name;

    /** 用户昵称 **/
    @Schema(description = "用户昵称")
    @TableField(value = "nickname")
    private String nickname;

    /** 密码 **/
    @Schema(description = "密码")
    @TableField(value = "password")
    private String password;

    /** 开启 **/
    @Schema(description = "开启或者关闭")
    @Getter(AccessLevel.NONE)
    @TableField(value = "enabled")
    private boolean enabled;

    /** 账号过期 **/
    @Schema(description = "账号过期")
    @Getter(AccessLevel.NONE)
    @TableField(value = "account_non_expired")
    private boolean accountNonExpired;

    /** 账号锁定 **/
    @Schema(description = "账号锁定")
    @Getter(AccessLevel.NONE)
    @TableField(value = "account_non_locked")
    private boolean accountNonLocked;

    /** 凭证过期 **/
    @Schema(description = "凭证过期")
    @Getter(AccessLevel.NONE)
    @TableField(value = "credentials_non_expired")
    private boolean credentialsNonExpired;

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

    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 创建时间 **/
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 **/
    @Schema(description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除状态：0、已删除 1、未删除 **/
    @Schema(description = "删除状态：0、已删除 1、未删除")
    @TableField(value = "delete")
    private int delete;

}
