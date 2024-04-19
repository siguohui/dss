package com.xiaosi.wx.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaosi.wx.entity.Stu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
