package com.fxlabs.fxt;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            Response<Project> projectResponse = projectService.save(new Project("Default", null));
            projectService.save(new Project("Project-2", null));
            System.out.println("Project: " + projectResponse.getData());

            // Env
            ProjectCredential projectCredential = new ProjectCredential("Default", "Basic", "admin", "admin123");
            List<ProjectCredential> projectCredentials = new ArrayList<>();
            projectCredentials.add(projectCredential);

            Response<ProjectEnvironment> projectEnvironmentResponse = projectEnvironmentService.save(new ProjectEnvironment(projectResponse.getData(), "Default", null, "http://locahost:8080/api/v1", projectCredentials));

            // Datasets
            ProjectDataSet ds = new ProjectDataSet();

            projectDataSetService.save(new ProjectDataSet(projectResponse.getData(), "User-Create-1", "/users", "POST", "{}", null, "", Arrays.asList("V1")));
            projectDataSetService.save(new ProjectDataSet(projectResponse.getData(), "User-Create-2", "/users", "POST", "{}", null, "", Arrays.asList("V1")));

            // Jobs
            Response<ProjectJob> projectJobResponse = projectJobService.save(new ProjectJob(projectResponse.getData(), "Default", null, projectEnvironmentResponse.getData(), Arrays.asList("V1"), "Default"));

            // Run
            runService.run(projectJobResponse.getData().getId());

        };
    }

}
