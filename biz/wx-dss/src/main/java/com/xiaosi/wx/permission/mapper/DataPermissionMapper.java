package com.xiaosi.wx.permission.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.ResultHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DataPermissionMapper<T> extends BaseMapper<T> {
    @Override
    @DssDataPermission
    int insert(T entity);

    @Override
    @DssDataPermission
    int deleteById(Serializable id);

    @Override
    @DssDataPermission
    int deleteById(T entity);

    @Override
    @DssDataPermission
    default int deleteByMap(Map<String, Object> columnMap) {
        return this.delete((Wrapper) Wrappers.query().allEq(columnMap));
    }

    @Override
    @DssDataPermission
    int delete(@Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    int deleteBatchIds(@Param("coll") Collection<?> idList);

    @Override
    @DssDataPermission
    int updateById(@Param("et") T entity);

    @Override
    @DssDataPermission
    int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper);

    @Override
    @DssDataPermission
    default int update(@Param("ew") Wrapper<T> updateWrapper) {
        return this.update((T) null, updateWrapper);
    }

    @Override
    @DssDataPermission
    T selectById(Serializable id);

    @Override
    @DssDataPermission
    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    @Override
    @DssDataPermission
    void selectBatchIds(@Param("coll") Collection<? extends Serializable> idList, ResultHandler<T> resultHandler);

    @Override
    @DssDataPermission
    default List<T> selectByMap(Map<String, Object> columnMap) {
        return this.selectList((Wrapper)Wrappers.query().allEq(columnMap));
    }

    @Override
    @DssDataPermission
    default void selectByMap(Map<String, Object> columnMap, ResultHandler<T> resultHandler) {
        this.selectList((Wrapper)Wrappers.query().allEq(columnMap), resultHandler);
    }

    @Override
    @DssDataPermission
    default T selectOne(@Param("ew") Wrapper<T> queryWrapper) {
        return this.selectOne(queryWrapper, true);
    }

    @Override
    @DssDataPermission
    default T selectOne(@Param("ew") Wrapper<T> queryWrapper, boolean throwEx) {
        List<T> list = this.selectList(queryWrapper);
        int size = list.size();
        if (size == 1) {
            return list.get(0);
        } else if (size > 1) {
            if (throwEx) {
                throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + size);
            } else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }

    @Override
    @DssDataPermission
    default boolean exists(Wrapper<T> queryWrapper) {
        Long count = this.selectCount(queryWrapper);
        return null != count && count > 0L;
    }

    @Override
    @DssDataPermission
    Long selectCount(@Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    List<T> selectList(@Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    void selectList(@Param("ew") Wrapper<T> queryWrapper, ResultHandler<T> resultHandler);

    @Override
    @DssDataPermission
    List<T> selectList(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    void selectList(IPage<T> page, @Param("ew") Wrapper<T> queryWrapper, ResultHandler<T> resultHandler);

    @Override
    @DssDataPermission
    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    void selectMaps(@Param("ew") Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler);

    @Override
    @DssDataPermission
    List<Map<String, Object>> selectMaps(IPage<? extends Map<String, Object>> page, @Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    void selectMaps(IPage<? extends Map<String, Object>> page, @Param("ew") Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler);

    @Override
    @DssDataPermission
    <E> List<E> selectObjs(@Param("ew") Wrapper<T> queryWrapper);

    @Override
    @DssDataPermission
    <E> void selectObjs(@Param("ew") Wrapper<T> queryWrapper, ResultHandler<E> resultHandler);

    @Override
    @DssDataPermission
    default <P extends IPage<T>> P selectPage(P page, @Param("ew") Wrapper<T> queryWrapper) {
        page.setRecords(this.selectList(page, queryWrapper));
        return page;
    }

    @Override
    @DssDataPermission
    default <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, @Param("ew") Wrapper<T> queryWrapper) {
        page.setRecords(this.selectMaps(page, queryWrapper));
        return page;
    }
}
