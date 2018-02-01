package com.fxlabs.fxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.fxlabs.fxt.dao.repository.jpa")
@ComponentScan(basePackages = {"com.fxlabs.fxt"})
public class FxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxtApplication.class, args);
    }

}

