package com.xiaosi.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

/**
 * @author diitich
 * @param <T>
 */
public interface RootMapper<T> extends BaseMapper<T> {

    /**
     * 批量新增
     * @param batchList
     * @return
     */
    int insertBatch(@Param("list") Collection<T> batchList);

    /**
     * 批量跟新
     * @param batchList
     * @return
     */
    int updateBatch(@Param("list")Collection<T> batchList);

}
