package com.xiaosi.wx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xiaosi.wx.annotation.EncryptedColumn;
import com.xiaosi.wx.annotation.EncryptedTable;
import com.xiaosi.wx.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user",autoResultMap = true)
@Schema(description = "用户")
@Accessors(chain = true)
@EncryptedTable
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
    private boolean accountNonExpired = true;

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /** 账号锁定 **/
    @Schema(description = "账号锁定")
    @TableField(value = "account_non_locked")
    @Getter(AccessLevel.NONE)
    private boolean accountNonLocked = true;

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /** 凭证过期 **/
    @Schema(description = "凭证过期")
    @TableField(value = "credentials_non_expired")
    @Getter(AccessLevel.NONE)
    private boolean credentialsNonExpired = true;

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /** 用户昵称 **/
    @Schema(description = "用户昵称")
    @TableField(value = "nick_name")
    private String nickName;

    /** 性别：0、女 1、男 **/
    @Schema(description = "性别：0、女 1、男")
    @TableField(value = "sex")
    private int sex;

    /** 手机号 **/
    @Schema(description = "手机号")
    @TableField(value = "mobile")
    @EncryptedColumn
    private String mobile;

    /** 邮箱 **/
    @Schema(description = "邮箱")
    @TableField(value = "email")
    private String email;

    /** 地址 **/
    @Schema(description = "地址")
    @TableField(value = "addr")
    @EncryptedColumn
    private String addr;

    /** 头像 **/
    @Schema(description = "头像")
    @TableField(value = "avatar")
    private String avatar;

    /** 部门ID **/
    @Schema(description = "部门ID")
    @TableField(value = "dept_id")
    private Long deptId;

    /** 访问策略 **/
    @Schema(description = "访问策略")
    @TableField(value = "access_policy")
    private boolean accessPolicy;

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


//    @TableField(exist = false)
//    @Getter(AccessLevel.NONE)
//    private Collection<GrantedAuthority> authorities;

    // 这是权限
    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        //authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }*/

    // 角色信息
    @TableField(exist = false)
    private Set<SysRole> roleSet = new HashSet<>();

    // 菜单信息
    @TableField(exist = false)
    private Set<SysMenu> menuSet = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将权限告知SpringSecurity，通过lambda表达式将Set<String>转成Collection<GrantedAuthority>
        return menuSet.stream().map(SysMenu::getPerms).filter(StringUtils::hasText).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }


    public  Set<Long> getRoleIds() {
        return roleSet.stream().map(m->m.getId()).collect(Collectors.toSet());
    }


    public Set<String> getPerms() {
        return menuSet.stream().map(m->m.getPerms()).collect(Collectors.toSet());
    }
}
