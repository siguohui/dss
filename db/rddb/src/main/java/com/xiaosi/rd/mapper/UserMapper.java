package com.xiaosi.rd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.rd.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> queryAll();
}
