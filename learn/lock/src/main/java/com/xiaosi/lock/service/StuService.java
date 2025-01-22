package com.xiaosi.lock.service;

import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaosi.lock.entity.Stu;
import com.xiaosi.lock.mapper.StuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuService {

    @Autowired
    private StuMapper stuMapper;

    @Lock4j(keys = {"#name"}, expire = 5000, acquireTimeout = 1000)
    public synchronized void addStu(String name) throws InterruptedException {
        Thread.sleep(4000);
        QueryWrapper<Stu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_name", name);
        List<Stu> stus = stuMapper.selectList(queryWrapper);
        Thread.sleep(4000);
        if(stus.isEmpty()){
            Stu stu = new Stu();
            stu.setStuName(name);
            stu.setAge(18);
            stuMapper.insert(stu);
        } else {
            System.out.println("已存在");
        }
    }
}
