package com.xiaosi.oracle.controller;

import com.google.common.collect.Lists;
import com.xiaosi.oracle.entity.Emp;
import com.xiaosi.oracle.mapper.EmpMapper;
import com.xiaosi.oracle.mapper.UserMapper;
import com.xiaosi.oracle.service.EmpService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Resource
    private UserMapper userMapper;
    @Resource
    private EmpService empService;

    @GetMapping("/index")
    public int index() {
        List<Emp> empList = Lists.newArrayList();

        int res =0;
        List<String> tables = userMapper.selectTables();
        for (String table : tables) {
           int num =  userMapper.selectData(table);
           Emp emp = new Emp();
           emp.setTableName(table);
           emp.setDataSize(num);
           empList.add(emp);
           res = res + num;
        }
        empService.saveBatch(empList);
        return res;
    }
}
