package com.xiaosi.wx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.PageX;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.mapper.StuMapper;
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
 * @since 2024-04-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/stu")
public class StuController {

    private final StuMapper stuMapper;

    @PageX
    @GetMapping("/list")
    public String getList() {
        return "1";
    }
}
