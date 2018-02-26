package com.fxlabs.fxt.vc.git.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Intesar Shannan Mohammed
 */
@SpringBootApplication(scanBasePackages = {"com.fxlabs.fxt"})
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}