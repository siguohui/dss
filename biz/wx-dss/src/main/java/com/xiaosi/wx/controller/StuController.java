package com.xiaosi.wx.controller;

import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.enums.LevelEnum;
import com.xiaosi.wx.service.StuService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/add")
    public Stu addStu(@RequestBody Stu stu){
        stuService.save(stu);
        return stu;
    }

    @SneakyThrows
    @PostMapping("/upload")
    public void upload(@RequestPart("file") MultipartFile file) {
        stuService.upload(file);
    }
}
