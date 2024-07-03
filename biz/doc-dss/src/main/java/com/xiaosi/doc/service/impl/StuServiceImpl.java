package com.xiaosi.doc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.xiaosi.doc.entity.Stu;
import com.xiaosi.doc.mapper.StuMapper;
import com.xiaosi.doc.service.StuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sgh
 * @since 2024-06-25
 */
@Service
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {

    @Override
    public void testTransactional() {
        Stu stu = Stu.builder().name("zhangsan").teaId(1L).build();
        this.save(stu);
        System.out.println(stu);
        List<Stu> stu1 = baseMapper.selectAll(stu);
        System.out.println(stu1);
    }
}
