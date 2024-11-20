package com.xiaosi.mul.datasource.Controller;

import com.xiaosi.mul.datasource.entity.Stu;
import com.xiaosi.mul.datasource.mapper.db2.Stu1Mapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Resource
    private Stu1Mapper stu1Mapper;

    @RequestMapping("/user")
    public List<Stu> userList(){
        return stu1Mapper.queryAll();
    }
}
