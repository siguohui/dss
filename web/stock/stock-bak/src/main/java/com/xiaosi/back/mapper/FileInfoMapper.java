package com.xiaosi.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaosi.back.utils.MybatisPlusRedisCache;
import com.xiaosi.back.entity.FileInfo;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation= MybatisPlusRedisCache.class,eviction=MybatisPlusRedisCache.class)
public interface FileInfoMapper extends BaseMapper<FileInfo> {
}
