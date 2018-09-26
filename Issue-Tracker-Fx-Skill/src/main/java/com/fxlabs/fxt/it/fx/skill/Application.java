package com.fxlabs.fxt.it.git.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Mohammed Shoukath Ali
 */
@SpringBootApplication(scanBasePackages = {"com.fxlabs.fxt"})
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}