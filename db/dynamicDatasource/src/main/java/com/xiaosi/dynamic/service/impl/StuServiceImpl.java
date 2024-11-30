package com.xiaosi.dynamic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosi.dynamic.annotation.DS;
import com.xiaosi.dynamic.entity.Stu;
import com.xiaosi.dynamic.mapper.StuMapper;
import com.xiaosi.dynamic.service.StuService;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class StuServiceImpl extends ServiceImpl<StuMapper,Stu> implements StuService {
}
