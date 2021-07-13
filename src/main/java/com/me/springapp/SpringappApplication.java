package com.me.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
//@EnableSwagger2 //Enable swagger 2.0 spec
//@EnableOpenApi //Enable open api 3.0.3 spec
public class SpringappApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}
}
