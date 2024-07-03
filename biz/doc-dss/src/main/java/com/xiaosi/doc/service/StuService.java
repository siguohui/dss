package com.xiaosi.doc.service;

import com.xiaosi.doc.entity.Stu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sgh
 * @since 2024-06-25
 */
public interface StuService extends IService<Stu> {

    void testTransactional();

}
