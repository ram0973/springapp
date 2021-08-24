package com.me.springapp.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class SpringdocConfig implements WebMvcConfigurer {

//    @Bean
//    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
//        return new OpenAPI()
//            .components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
//                .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1")).addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema())))
//            .info(new Info()
//                .title("Petstore API")
//                .version(appVersion)
//                .description("This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key `special-key` to test the authorization filters.")
//                .termsOfService("http://swagger.io/terms/")
//                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*.html")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
            .resourceChain(false)
            .addResolver(new WebJarsResourceResolver())
            .addResolver(new PathResourceResolver());
    }
}

