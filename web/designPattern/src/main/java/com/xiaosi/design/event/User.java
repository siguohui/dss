package com.xiaosi.design.event;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class User {

    private String name;
    private String email;

    @Tolerate
    public User() {
    }
}
