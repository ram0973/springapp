package com.me.springapp.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.service.ApiInfo;
//
//import java.util.Collections;
//
//@Configuration
public class SwaggerConfig {
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//            "Spring REST API example",
//            "Springapp Swagger",
//            "1.0",
//            "",
//            null,
//            "License of API - MIT",
//            "",
//            Collections.emptyList());
//    }

//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//            .apiInfo(apiInfo())
//            .securityContexts(Collections.singletonList(securityContext()))
//            .securitySchemes(Collections.singletonList(
//                HttpAuthenticationScheme
//                .JWT_BEARER_BUILDER
//                .name("Authorization")
//                .build()))
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build();
//    }
}
