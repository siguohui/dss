package com.xiaosi.wx.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("一个API接口文档")
                        // 接口文档简介
                        .description("这是基于Knife4j OpenApi3的测试接口文档")
                        // 接口文档版本
                        .version("1.0版本")
                        // 开发者联系方式
                        .contact(new Contact().name("张乔")
                                .email("000000000@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot3测试knife4j")
                        .url("http://127.0.0.1:8888"));
    }

}