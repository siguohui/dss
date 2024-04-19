package com.xiaosi.wx;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() {
        System.out.println(dataSource.getClass());
        DruidDataSource ds = (DruidDataSource)dataSource;
        System.out.println(ds.getInitialSize());
        System.out.println(ds.getMaxActive());
    }
}
