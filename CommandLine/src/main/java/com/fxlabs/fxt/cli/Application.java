package com.fxlabs.fxt.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fxlabs.fxt.cli", "com.fxlabs.fxt.sdk", "com.fxlabs.fxt.codegen"})
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}