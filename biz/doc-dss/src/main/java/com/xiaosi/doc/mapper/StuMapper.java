package com.xiaosi.doc.mapper;

import com.xiaosi.doc.entity.Stu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sgh
 * @since 2024-06-25
 */
public interface StuMapper extends BaseMapper<Stu> {

    List<Stu> selectAll(Stu byId);
}
