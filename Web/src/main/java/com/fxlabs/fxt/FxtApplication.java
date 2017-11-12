package com.fxlabs.fxt;

import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.project.ProjectDataSetService;
import com.fxlabs.fxt.services.project.ProjectEnvironmentService;
import com.fxlabs.fxt.services.project.ProjectJobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fxlabs.fxt"})
public class FxtApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxtApplication.class, args);
    }

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectEnvironmentService projectEnvironmentService;

    @Autowired
    ProjectDataSetService projectDataSetService;

    @Autowired
    ProjectJobService projectJobService;

    @Autowired
    RunService runService;

    @Bean
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
            ProjectCredential projectCredential = new ProjectCredential("Default", "Basic", "admin", "admin123");
            List<ProjectCredential> projectCredentials = new ArrayList<>();
            projectCredentials.add(projectCredential);

            Response<ProjectEnvironment> projectEnvironmentResponse = projectEnvironmentService.save(new ProjectEnvironment(projectDto, "Default", null, "http://localhost:8080/api/v1", projectCredentials));

            // Datasets
            ProjectDataSet ds = new ProjectDataSet();

            projectDataSetService.save(new ProjectDataSet(projectDto, "User-Create-1", "/users", "POST", "{\n" +
                    "    \"name\":\"Bob Lee\",\n" +
                    "    \"username\":\"bob\",\n" +
                    "    \"email\":\"bob@fxlabs.com\",\n" +
                    "    \"company\":\"FxLabs\",\n" +
                    "    \"location\":\"San Francisco\",\n" +
                    "    \"title\":\"Designer\"\n" +
                    "  }", null, "", Arrays.asList("V1")));
            projectDataSetService.save(new ProjectDataSet(projectDto, "User-Create-2", "/users", "POST", "{\n" +
                    "    \"name\":\"Foo Lee\",\n" +
                    "    \"username\":\"foo\",\n" +
                    "    \"email\":\"foo@fxlabs.com\",\n" +
                    "    \"company\":\"FxLabs\",\n" +
                    "    \"location\":\"San Francisco\",\n" +
                    "    \"title\":\"Designer\"\n" +
                    "  }", null, "", Arrays.asList("V1")));

            // Jobs
            Response<ProjectJob> projectJobResponse = projectJobService.save(new ProjectJob(projectDto, "Default", null, projectEnvironmentResponse.getData(), Arrays.asList("V1"), "fx-default-queue"));

            // Run
            //runService.run(projectJobResponse.getData().getId());

        };
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
        return new InMemoryUserDetailsManager(Collections.singleton(User.withUsername("admin").roles("USER").password("admin123").build()));
    }
}
