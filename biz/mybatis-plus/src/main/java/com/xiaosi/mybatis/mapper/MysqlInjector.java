package com.xiaosi.mybatis.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import java.util.List;

public class MysqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methods = super.getMethodList(mapperClass,tableInfo);
        // 自定义的insert SQL注入器
        methods.add(new InsertBatchColumn());
        // 自定义的update SQL注入器，参数需要与RootMapper的批量update名称一致
        methods.add(new UpdateBatchColumn("updateBatch"));
        return methods;
    }
}
