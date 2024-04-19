package com.xiaosi.wx.controller;

import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.mapper.StuMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@Tag(name = "登录接口")
public class LoginController {

    @Setter(onMethod_ = @Autowired)
    private RedisTemplate<String, Object> redisTemplate;

    @Setter(onMethod_ = @Autowired)
    private StuMapper stuMapper;

    @Operation(summary = "登录接口")
    @GetMapping("/login")
    public List<Stu> login(){
        ValueOperations<String, Object> op = redisTemplate.opsForValue();
        op.set("name","xiaosi");
        return stuMapper.selectList(null);
    }
}
