package com.fxlabs.fxt.vc.git.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication(scanBasePackages = {"com.fxlabs.fxt"})
@PropertySource(ignoreResourceNotFound = false, value = "classpath:/fx-vc-skill.properties")
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}