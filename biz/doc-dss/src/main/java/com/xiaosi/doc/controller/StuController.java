package com.xiaosi.doc.controller;

import com.xiaosi.doc.entity.Stu;
import com.xiaosi.doc.service.StuService;
import lombok.RequiredArgsConstructor;
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
 * @since 2024-06-25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class StuController {

    private final StuService stuService;

    @GetMapping("/index")
    public String index(){
         stuService.testTransactional();
         return "success";
    }

}
