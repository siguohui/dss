package com.xiaosi.wx.service;

import com.xiaosi.wx.entity.Stu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sgh
 * @since 2024-05-11 06:07:12
 */
public interface StuService extends IService<Stu> {

    void upload(MultipartFile file);
}
