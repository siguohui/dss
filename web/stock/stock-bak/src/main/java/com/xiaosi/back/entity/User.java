package com.xiaosi.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(schema = "sgh", name = "sys_user")
@Accessors(chain = true)
public class User extends BaseEntity{

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
}
