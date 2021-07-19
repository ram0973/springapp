package com.me.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class SpringappApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}
}
