package com.xiaosi.dynamic.config;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DateSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.slave1")
//    public DataSource slaveDataSource1(){
//        return DruidDataSourceBuilder.create().build();
//    }


//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.slave2")
//    public DataSource slaveDataSource2(){
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource createDynamicDataSource(){
        Map<Object,Object> dataSourceMap = new HashMap<>();
        DataSource defaultDataSource = masterDataSource();
        dataSourceMap.put("master",defaultDataSource);
//        dataSourceMap.put("slave1",slaveDataSource1());
//        dataSourceMap.put("slave2",slaveDataSource2());
        return new DynamicDataSource(defaultDataSource,dataSourceMap);
    }
}
