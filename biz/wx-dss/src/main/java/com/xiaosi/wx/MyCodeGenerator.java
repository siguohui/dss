package com.xiaosi.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.xiaosi.wx.base.BaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Types;
import java.util.Collections;
//https://blog.csdn.net/duanluan/article/details/118776180
public class MyCodeGenerator {

    public static final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/db_security?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8";
    public static final String username = "root";
    public static final String password = "root";
    public static String module="";
    public static String parent_package="com.xiaosi.wx";
    public static String superEntity="com.xiaosi.wx.base.BaseEntity";

    public static void main(String[] args) {



        FastAutoGenerator.create(jdbcUrl, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("sgh") // 设置作者
                            .enableSpringdoc()
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\java") // 指定输出目录
//                            .disableOpenDir()
                    ;
                })
                //类型映射配置
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    //自定义类型转换配置
                    if (typeCode == Types.SMALLINT || typeCode == Types.TINYINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(parent_package) // 设置父包名
                            .entity("model")               // 实体包名
                            .service("service")             // service包名
                            .serviceImpl("service.impl")    // service实现类包名
                            .xml("mapper")                  // dao层包名
                            .controller("controller")       // controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                //自定义配置
                /*.injectionConfig(builder -> {
                    builder.customFile(consumer-> consumer
                            .fileName("Mapper.xml")
                            .filePath(Config.xmlPath.toString())
                            .enableFileOverride()
                            .templatePath("/templates/mapper.xml.ftl"))
                    ;
                })*/
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("sys_persistent_login") // 设置需要生成的表名
                            .addTablePrefix("") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .superClass(superEntity)
                            .enableChainModel()
                            .enableTableFieldAnnotation()
                            .idType(IdType.ASSIGN_ID)    //id类型
                            .enableLombok() //开启 Lombok
                            .enableFileOverride() // 覆盖已生成文件
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命\
//                            .versionColumnName("version")           // 乐观锁
//                            .logicDeleteColumnName("deleted")       // 逻辑删除字段
//                            .addTableFills(new Column("gmt_create", FieldFill.INSERT))      // 新增时填充
//                            .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE)) // 新增或修改时填充
                            .enableTableFieldAnnotation()

                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableBaseResultMap() // 通用map
                            .superClass(BaseMapper.class)   // 继承 BaseMapper
                            .formatMapperFileName("%sMapper")   // dao层命名规则
                            .formatXmlFileName("%sMapper")  // xml映射文件命名规则
                            .mapperAnnotation(Mapper.class) // dao层添加@Mapper注解
                            .enableFileOverride() // 覆盖已生成文件

                            // Service 策略配置
                            .serviceBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            // Controller 策略配置
                            .controllerBuilder()
                            .enableFileOverride()
//                            .enableHyphenStyle()
                            .formatFileName("%sController") // controller命名规则
                            .enableRestStyle() //驼峰命名
                            .enableFileOverride() // 覆盖已生成文件
                    ;
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker模板引擎
//                .templateConfig(builder -> builder.controller(""))//关闭生成controller
                 //模板配置，如果你没有自定义的一些模板配置，这里直接使用默认即可。
//                .templateConfig(config->config.entity("/templates/entity"))
                .execute();

    }
}
