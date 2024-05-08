package com.xiaosi.wx.controller;

import com.xiaosi.wx.pojo.JsonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "登录接口")
public class LoginController {

//  @PreAuthorize("hasRole('ADMIN')")
//    @Cacheable(value = "dept",key = "#name")
    @PostMapping("/refresh")
    public JsonResult refresh() {
        return JsonResult.success("index");
    }
}
