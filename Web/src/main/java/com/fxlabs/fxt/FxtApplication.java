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

    @Autowired
    ProjectService projectService;

    @Autowired
    EnvironmentService projectEnvironmentService;

    @Autowired
    TestSuiteService projectDataSetService;

    @Autowired
    JobService projectJobService;

    @Autowired
    RunService runService;

    //@Bean
    /*
    InitializingBean sendDatabase() {
        return () -> {
            // Project
            Project project = projectService.save(new Project("Default", null)).getData();
            projectService.save(new Project("Project-2", null));
            System.out.println("Project: " + project);
            NameDto projectDto = new NameDto();
            projectDto.setId(project.getId());
            projectDto.setVersion(project.getVersion());

            // Env
            Auth projectCredential = new Auth("Default", "Basic", "admin", "admin123");
            List<Auth> projectCredentials = new ArrayList<>();
            projectCredentials.add(projectCredential);

            Response<Environment> projectEnvironmentResponse = projectEnvironmentService.save(new Environment(projectDto, "Default", null, "http://localhost:8080/api/v1", projectCredentials));

            // Datasets
            TestSuite ds = new TestSuite();

            projectDataSetService.save(new TestSuite(projectDto, "User-Create-1", "/users", "POST", Arrays.asList("{\n" +
                    "    \"name\":\"Bob Lee\",\n" +
                    "    \"username\":\"bob\",\n" +
                    "    \"email\":\"bob@fxlabs.com\",\n" +
                    "    \"company\":\"FxLabs\",\n" +
                    "    \"location\":\"San Francisco\",\n" +
                    "    \"title\":\"Designer\"\n" +
                    "  }"), null, Arrays.asList("V1")));


            projectDataSetService.save(new TestSuite(projectDto, "User-Create-2", "/users", "POST", Arrays.asList("{\n" +
                    "    \"name\":\"Foo Lee\",\n" +
                    "    \"username\":\"foo\",\n" +
                    "    \"email\":\"foo@fxlabs.com\",\n" +
                    "    \"company\":\"FxLabs\",\n" +
                    "    \"location\":\"San Francisco\",\n" +
                    "    \"title\":\"Designer\"\n" +
                    "  }"), null, Arrays.asList("V1")));

            // Jobs
            Response<Job> projectJobResponse = projectJobService.save(new Job(projectDto, "Default", null, projectEnvironmentResponse.getData(), Arrays.asList("V1"), "fx-default-queue"));

            // Run
            //runService.run(projectJobResponse.getData().getId());

        };
    }*/

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
