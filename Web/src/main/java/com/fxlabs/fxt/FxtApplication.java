package com.fxlabs.fxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fxlabs.fxt"})
public class FxtApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxtApplication.class, args);
	}
}
