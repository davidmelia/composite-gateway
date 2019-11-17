package com.example.demo;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Exchange Service API", description = "Exchange Service returns meta data for all support exchanges."),
    tags = {@Tag(name = "Exchange Times", description = "Exhange Times represents all of the open / closed times across all support exchanges")})
public class SwaggerConfig {

//  @Bean
//  public OpenApiCustomiser microTypeOpenApiCustomiser() {
//    return openApi -> openApi.getComponents().addSchemas("AccountId", new StringSchema()).addSchemas("CustomerId", new StringSchema());
//  }

}
