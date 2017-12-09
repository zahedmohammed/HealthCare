package com.fxlabs.fxt;

import com.fxlabs.fxt.services.project.TestSuiteService;
import com.fxlabs.fxt.services.project.EnvironmentService;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.*;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.fxlabs.fxt"})
public class FxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxtApplication.class, args);
    }

}

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    UserDetailsService user() {
        return new InMemoryUserDetailsManager(
                Arrays.asList(
                        User.withUsername("admin").roles("USER", "SUPERUSER").password("admin123").build(),
                        User.withUsername("user1").roles("USER").password("user1").build(),
                        User.withUsername("user2").roles("USER").password("user2").build()
                ));
    }
}
