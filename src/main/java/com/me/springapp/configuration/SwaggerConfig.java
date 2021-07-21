package com.me.springapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Configuration
public class SwaggerConfig {

    //private ApiKey apiKey() {
    //    return new ApiKey("Bearer", "Authorization", "header");
    //}

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "REST API",
            "",
            "1.0",
            "Terms of service",
            new Contact("Ramil Yabbarov", "yabbarov.ru", "ramil@yabbarov.ru"),
            "License of API - MIT",
            "",
            Collections.emptyList());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .securityContexts(Collections.singletonList(securityContext()))
            .securitySchemes(Collections.singletonList(
                HttpAuthenticationScheme
                .JWT_BEARER_BUILDER
                .name("Authorization")
                .build()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }

//    public Docket api() {
//        HttpAuthenticationScheme authenticationScheme = HttpAuthenticationScheme
//            .JWT_BEARER_BUILDER
//            .name("Authorization")
//            .build();
//        return new Docket(DocumentationType.OAS_30)
//            .apiInfo(apiInfo())
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.ant(context.getContextPath() + "/api/**"))
//            .build()
//            .securityContexts(Collections.singletonList(securityContext()))
//            .securitySchemes(Collections.singletonList(authenticationScheme));
//    }
}
