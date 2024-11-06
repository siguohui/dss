package com.xiaosi.dynamic;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xiaosi.dynamic.config.DynamicDataSource;
import com.xiaosi.dynamic.entity.DataSourceEntity;
import com.xiaosi.dynamic.entity.DbInfo;
import com.xiaosi.dynamic.mapper.DbInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadDataSourceRunner implements CommandLineRunner {
    @Resource
    private DynamicDataSource dynamicDataSource;
    @Resource
    private DbInfoMapper dbInfoMapper;
    @Override
    public void run(String... args) throws Exception {
        List<DbInfo> testDbInfos = dbInfoMapper.selectList(null);
        if (CollectionUtils.isNotEmpty(testDbInfos)) {
            List<DataSourceEntity> ds = new ArrayList<>();
            for (DbInfo dbInfo : testDbInfos) {
                DataSourceEntity sourceEntity = new DataSourceEntity();
                BeanUtils.copyProperties(dbInfo,sourceEntity);
                sourceEntity.setKey(dbInfo.getName());
                ds.add(sourceEntity);
            }
            dynamicDataSource.createDataSource(ds);
        }
    }
}
