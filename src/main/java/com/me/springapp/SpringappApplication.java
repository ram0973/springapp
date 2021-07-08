package com.me.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@EnableSwagger2
public class SpringappApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}
}
