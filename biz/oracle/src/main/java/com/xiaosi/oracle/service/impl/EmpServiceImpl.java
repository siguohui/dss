package com.xiaosi.oracle.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaosi.oracle.entity.Emp;
import com.xiaosi.oracle.mapper.EmpMapper;
import com.xiaosi.oracle.service.EmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sgh
 * @since 2024-10-30
 */
@DS("slave01")
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

}
