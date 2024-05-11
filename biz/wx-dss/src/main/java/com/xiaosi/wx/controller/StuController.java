package com.xiaosi.wx.controller;

import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.service.StuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgh
 * @since 2024-05-11 06:07:12
 */
@RestController
@RequestMapping("/stu")
public class StuController {

    @Resource
    StuService stuService;

    @GetMapping("/list")
    public List<Stu> getList(){
        return stuService.list();
    }

}
