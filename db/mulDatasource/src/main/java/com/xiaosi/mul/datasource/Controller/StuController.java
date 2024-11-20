package com.xiaosi.mul.datasource.Controller;

import com.xiaosi.mul.datasource.entity.Stu;
import com.xiaosi.mul.datasource.mapper.db1.StuMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class StuController {

    @Resource
    private StuMapper stuMapper;
    @Resource
    private List<DataSource> dataSources;

    @RequestMapping("/stu")
    public List<Stu> stuList(){
        dataSources.forEach(f->{
            System.out.println(f);
        });
        return stuMapper.queryAll();
    }
}
