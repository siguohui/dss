package com.xiaosi.wx.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.PageX;
import com.xiaosi.wx.entity.Stu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.wx.vo.StuVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sgh
 * @since 2024-04-19
 */
@DS("master")
public interface StuMapper extends BaseMapper<Stu> {
    @PageX
    List<Stu> getAllPage(StuVo stu);

}
