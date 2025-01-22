package com.xiaosi.mybatis.controller;

import com.xiaosi.mybatis.entity.Stu;
import com.xiaosi.mybatis.mapper.StuMapper;
import com.xiaosi.mybatis.service.StuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgh
 * @since 2024-10-30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stu")
public class StuController {

    private final StuService stuService;
    private final StuMapper stuMapper;

    @GetMapping("save")
    public String index(){
        List<Stu> userList = new ArrayList<>();
        for(int i = 0; i < 199999; i++){
            Stu user = new Stu();
            user.setName("张三");
            user.setTeaId((long) i);
            userList.add(user);
        }
        long s = System.currentTimeMillis();
        stuMapper.insertBatch(userList);
        System.out.println("保存200000条数据消耗" + (System.currentTimeMillis() - s) + "ms");
        return "success";
    }

}
