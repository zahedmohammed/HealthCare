package com.fxlabs.issues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication
//@EnableScheduling
@EnableJpaRepositories(basePackages = "com.fxlabs.issues.dao.repository.jpa")
@ComponentScan(basePackages = {"com.fxlabs.issues"})
public class FxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxtApplication.class, args);
    }

}

