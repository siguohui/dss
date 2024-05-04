# 1

当通过Lombok的@Data注解生成get/set方法时，如果不想生成username的get/set方法时，可以在username字段上加上注解
@Getter(AccessLevel.NONE) / @Setter(AccessLevel.NONE)

public class Manager implements UserDetails {

@Id
private Long managerId;
private String fullName;
@Getter(AccessLevel.NONE)
private String username;
@Getter(AccessLevel.NONE)
private String password;
private String avatar;
private Integer gender;
private String phoneNumber;
private Long roleId;
private Long companyId;
private Long createManagerId;
private LocalDateTime createTime;
private Long updateManagerId;
private LocalDateTime updateTime;
@Column("IS_DELETED")
private Integer deleted;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
return null;
}

@Override
public String getPassword() {
return this.password;
}

@Override
public String getUsername() {
return this.username;
}

@Override
public boolean isAccountNonExpired() {
return true;
}

@Override
public boolean isAccountNonLocked() {
return true;
}

@Override
public boolean isCredentialsNonExpired() {
return true;
}

@Override
public boolean isEnabled() {
return this.getDeleted().intValue() == 0;
}
}
