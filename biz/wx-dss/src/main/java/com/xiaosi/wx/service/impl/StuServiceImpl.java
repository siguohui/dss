package com.xiaosi.wx.service.impl;

import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.mapper.StuMapper;
import com.xiaosi.wx.service.StuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosi.wx.vo.StuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sgh
 * @since 2024-04-19
 */
@Service
@RequiredArgsConstructor
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {

    private final StuMapper stuMapper;

    @Override
    public List<Stu> getListPage(StuVo stu) {
        return stuMapper.getAllPage(stu);
    }
}
