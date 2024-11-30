//package com.xiaosi.rd.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = "com.xiaosi.mul.datasource.mapper.db1", sqlSessionTemplateRef  = "sqlSessionTemplate1")
//public class DataSource1 {
//
//    @Value("${spring.datasource.type}")
//    private Class<? extends DataSource> dataSourceType;
//
//    @Value("${spring.datasource.druid-pool.initial-size}")
//    private int initialSize;
//    @Value("${spring.datasource.druid-pool.max-active}")
//    private int maxActive;
//    @Value("${spring.datasource.druid-pool.max-wait}")
//    private int maxWait;
//    @Value("${spring.datasource.druid-pool.min-idle}")
//    private int minIdle;
//
//    @Bean(name = "db1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    @Primary
//    public DataSource dataSource() {
//        /*return DataSourceBuilder.create().build();*/
//        DruidDataSource druidDataSource = (DruidDataSource) DataSourceBuilder.create().type(dataSourceType).build();
//        druidDataSource.setMaxActive(maxActive);
//        druidDataSource.setMaxWait(maxWait);
//        druidDataSource.setInitialSize(initialSize);
//        druidDataSource.setMinIdle(minIdle);
//        return druidDataSource;
//    }
//
//    @Bean(name = "sqlSessionFactory1")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory1(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
////        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
////        bean.setDataSource(dataSource);
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml"));
////        return bean.getObject();
//        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
//        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db1/*.xml"));
//        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//        mybatisConfiguration.setLogImpl(StdOutImpl.class);
//        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
//        return mybatisSqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "transactionManager1")
//    @Primary
//    public DataSourceTransactionManager transactionManager1(@Qualifier("db1DataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "sqlSessionTemplate1")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
