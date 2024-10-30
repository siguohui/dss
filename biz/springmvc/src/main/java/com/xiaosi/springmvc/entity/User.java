package com.xiaosi.springmvc.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    private Long userId;
    private String username;
    private String passworld;
    private String token;
}
