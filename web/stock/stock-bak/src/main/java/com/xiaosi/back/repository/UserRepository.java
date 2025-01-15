package com.xiaosi.back.repository;

import com.xiaosi.back.entity.User;

public interface UserRepository extends BaseRepostitory<User, Long> {
    User findByUsername(String username);
}
