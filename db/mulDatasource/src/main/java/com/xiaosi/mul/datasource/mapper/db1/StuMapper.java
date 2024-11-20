package com.xiaosi.mul.datasource.mapper.db1;

import com.xiaosi.mul.datasource.entity.Stu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface StuMapper {

    List<Stu> queryAll();
}
