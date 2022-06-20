package com.me.springapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@Configuration
public class SpringdocConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI customOpenAPI(@Value("1.0") String appVersion) {
        return new OpenAPI().info(new Info()
                .title("Rest HATEOAS application API")
                .version("1.0")
                .description("This is a sample Spring application")
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*/*.html")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
            .resourceChain(false)
            .addResolver(new WebJarsResourceResolver())
            .addResolver(new PathResourceResolver());
    }
}

