package com.xiaosi.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(schema = "sgh", name = "sys_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User extends BaseEntity{

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
}
