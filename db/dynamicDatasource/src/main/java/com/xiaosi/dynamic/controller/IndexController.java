package com.xiaosi.dynamic.controller;

import com.google.common.collect.Lists;
import com.xiaosi.dynamic.entity.Stu;
import com.xiaosi.dynamic.service.StuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Resource
    private StuService stuService;

    @GetMapping("/index")
    public List<Stu> getMasterData(){
//      DataSourceContextHolder.setDataSource(datasourceName);
        List<Stu> list = Lists.newArrayList();
        list.add(Stu.build(1L,50, null));
        list.add(Stu.build(2L,50, null));
        list.add(Stu.build(3L,50, null));
        list.add(Stu.build(4L,50, null));
        stuService.updateBatchById(list);
        return list;
    }
}
