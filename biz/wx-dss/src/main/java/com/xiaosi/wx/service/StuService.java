package com.xiaosi.wx.service;

import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.PageX;
import com.xiaosi.wx.entity.Stu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosi.wx.vo.StuVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sgh
 * @since 2024-04-19
 */
public interface StuService extends IService<Stu> {
    List<Stu> getListPage(StuVo stu);

}
