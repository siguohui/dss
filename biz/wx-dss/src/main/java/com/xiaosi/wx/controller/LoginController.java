package com.xiaosi.wx.controller;

import com.xiaosi.wx.pojo.JsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "登录接口")
public class LoginController {

    @Setter(onMethod_ = @Autowired)
    private RedisTemplate<String, Object> redisTemplate;

//  @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "dept",key = "#name")
    @GetMapping("/add")
    public JsonResult index(String bookName) {
        return JsonResult.success("index");
    }
}
