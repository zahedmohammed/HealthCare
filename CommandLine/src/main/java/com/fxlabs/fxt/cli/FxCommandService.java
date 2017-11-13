package com.fxlabs.fxt.cli;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Fx server connection details

    @Value("${fx.master.url}")
    private String fxMasterUrl;

    @Value("${fx.master.accessKey}")
    private String fxAccessKey;

    @Value("${fx.master.secretKey}")
    private String fxSecretKey;

    // Project

    @Value("${project.name}")
    private String projectName;

    // Test App details
    @Value("${env.name}")
    private String envName;
    @Value("${env.url}")
    private String envUrl;

    @Value("${env.auth.type}")
    private String envAuthType;

    @Value("${env.accessKey}")
    private String envUsername;

    @Value("${env.secretKey}")
    private String envPassword;

    // Job details
    @Value("${job.name}")
    private String jobName;

    @Value("${job.tags}")
    private List<String> jobTags;

    @Value("${job.region}")
    private String jobRegion = "fx-default-queue";


    @Autowired
    RestRespository respository;

    public void load() {
        try {
            // read fx server details

            // create project
            logger.info("loading project...");
            Project project = respository.saveProject(projectName, fxMasterUrl, fxAccessKey, fxSecretKey);
            logger.info("project created with id [{}]...", project.getId());

            // create env
            logger.info("loading env details...");
            ProjectEnvironment env = respository.saveEnv(project, envName, envUrl, envAuthType, envUsername, envPassword, fxMasterUrl, fxAccessKey, fxSecretKey);
            logger.info("env created with id [{}]...", env.getId());

            // read job
            logger.info("loading job details...");
            String id = respository.saveJob(project, jobName, env, jobTags, jobRegion, fxMasterUrl, fxAccessKey, fxSecretKey);
            logger.info("job created with id [{}]...", id);

            // create dataset

            logger.info("Successful!");

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }

    }
}
