package com.fxlabs.fxt.cloud.azure.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@SpringBootApplication(scanBasePackages = {"com.fxlabs.fxt"})
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}