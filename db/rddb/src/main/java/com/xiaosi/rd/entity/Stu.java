package com.xiaosi.rd.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "stu")
public class Stu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private LocalDateTime createTime;

    private Boolean hasleaf;

    private Integer age;
}
