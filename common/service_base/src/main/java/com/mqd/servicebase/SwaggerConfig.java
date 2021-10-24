package com.mqd.servicebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.mqd.eduservice.controller"))
//                .apis(RequestHandlerSelectors.basePackage("com.mqd.oss.controller"))
//                .paths(PathSelectors.ant("/admin/.*"))
//                .paths(PathSelectors.ant("/error.*"))
                .build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("Api中心文档")
                .description("定义了微服务接口")
                .version("1.0")
                .contact(new Contact("Mqd","http://localhost","1029259127@qq.com"))
                .build();
    }
}
