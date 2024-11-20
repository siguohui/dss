//
//package com.xiaosi.mul.datasource.config;
//
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import java.util.Properties;
//
//@Configuration
//@AutoConfigureAfter({MyBatisConfig.class})
//public class MyBatisMapperScannerConfig {
//
//    @Bean(name = "masterMapperScannerConfigurer")
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("masterSqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage(MyBatisConfig.PACKAGE);
//        Properties properties = new Properties();
//        properties.setProperty("mappers", "com.techhero.common.bean.mapper.BaseMapper");
//        properties.setProperty("notEmpty", "false");
//        properties.setProperty("IDENTITY", "SELECT SYS_GUID() FROM DUAL");
//        properties.setProperty("ORDER","BEFORE");
//        mapperScannerConfigurer.setProperties(properties);
//        return mapperScannerConfigurer;
//    }
//}
