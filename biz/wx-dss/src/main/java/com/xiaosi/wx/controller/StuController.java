package com.xiaosi.wx.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.PageX;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.mapper.StuMapper;
import com.xiaosi.wx.service.StuService;
import com.xiaosi.wx.vo.StuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgh
 * @since 2024-04-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stu")
public class StuController {

    private final StuService stuService;

    @PostMapping("/list")
    public List<Stu> getList(@RequestBody StuVo stu) {
        return stuService.getListPage(stu);
    }
}
