///*
// * The MIT License (MIT)
// *
// * Copyright (c) 2014-2016 abel533@gmail.com
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//
//package com.xiaosi.mul.datasource.config;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import com.techhero.base.supportplatform.utils.db.FactoryBuilderPluginsUtils;
//import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//public class MyBatisConfig {
//    /*Mapper扫描的包*/
//    public static final String PACKAGE = "com.xiaosi.mul.datasource.**.mapper";
//
//    /*MapperXml所在位置*/
//    private static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";
//
//    /*配置数据源*/
//    @Primary
//    @Bean(name = "masterDruidDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource masterDruidDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    /*配置会话工厂*/
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactoryBean() {
//        SqlSessionFactoryBean bean       = new SqlSessionFactoryBean();
//        DataSource            dataSource = masterDruidDataSource();
//        bean.setDataSource(dataSource);
//        bean.setDatabaseIdProvider(databaseIdProvider());
//        return FactoryBuilderPluginsUtils.builder(bean, MAPPER_LOCATION);
//    }
//
//    /*配置会话TempLate*/
//    @Bean(name = "masterSqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    /*配置事务管理器*/
//    @Primary
//    @Bean(name = "masterDataSourceTransactionManager")
//    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("masterDruidDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /*配置自动化数据请求查找给予MapperXml访问Oracle或者Mysql*/
//    @Bean(name = "databaseIdProvider")
//    public VendorDatabaseIdProvider databaseIdProvider() {
//        VendorDatabaseIdProvider vi = new VendorDatabaseIdProvider();
//        Properties               p  = new Properties();
//        p.setProperty("Oracle", "oracle");
//        p.setProperty("MySQL", "mysql");
//        p.setProperty("H2", "h2");
//        vi.setProperties(p);
//        return vi;
//    }
//
//}
