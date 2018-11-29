package com.fxlabs.issues;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication
//@EnableScheduling
@EnableJpaRepositories(basePackages = "com.fxlabs.issues.dao.repository.jpa")
@ComponentScan(basePackages = {"com.fxlabs.issues"})
@EnableAsync
public class FxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxtApplication.class, args);
    }


    @Bean
    public TextEncryptor encrypt() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPasswordCharArray("fx-secret".toCharArray());
        return encryptor;
    }

}

