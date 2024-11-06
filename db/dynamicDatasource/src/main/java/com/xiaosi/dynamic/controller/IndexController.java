package com.xiaosi.dynamic.controller;

import com.xiaosi.dynamic.annotation.DS;
import com.xiaosi.dynamic.entity.Stu;
import com.xiaosi.dynamic.config.DataSourceContextHolder;
import com.xiaosi.dynamic.mapper.StuMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Resource
    private StuMapper stuMapper;

    @DS("slave1")
    @GetMapping("/index")
    public List<Stu> getMasterData(){
//        DataSourceContextHolder.setDataSource(datasourceName);
        List<Stu> list = stuMapper.selectList(null);
        DataSourceContextHolder.removeDataSource();
        return list;
    }
}
