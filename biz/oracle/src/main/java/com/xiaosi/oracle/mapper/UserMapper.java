package com.xiaosi.oracle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper {

    @Select("SELECT TABLE_NAME FROM dba_tables WHERE OWNER = 'GHBY'")
    List<String> selectTables();

    @Select("SELECT count(1) FROM ${table}")
    int selectData(@Param("table") String table);
}
