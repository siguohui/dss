//package com.xiaosi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//@Configuration
//
//public class Knife4jConfig {
//    @Bean
//    public Docket Api() {
////        使用swagger2语法
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        //设置网站标题
//                        .title("用心健康接口文档")
//                        .description("信息")
//                        .termsOfServiceUrl("http://long.com")
//                        .contact(new Contact("龙", "http://long.com", "@qq.com"))
//                        //许可证
//                        .license("我许可")
//                        .licenseUrl("@qq.com")
//                        //版本
//                        .version("0.0.1-SNAPSHOT")
//                        .build())
//                // 组名
//                .groupName("default")
//                .select()
////                设置根包路径
//                .apis(RequestHandlerSelectors.basePackage("com.it"))
//                .paths(PathSelectors.any())
//                .build();
//        return docket;
//    }
//}
