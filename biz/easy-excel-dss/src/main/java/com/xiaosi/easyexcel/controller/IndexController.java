package com.xiaosi.easyexcel.controller;

import com.xiaosi.easyexcel.service.StuService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IndexController {

    @Resource
    private StuService stuService;

    @SneakyThrows
    @PostMapping("/upload")
    public void upload(@RequestPart("file")MultipartFile file) {
        stuService.upload(file);
    }
}
