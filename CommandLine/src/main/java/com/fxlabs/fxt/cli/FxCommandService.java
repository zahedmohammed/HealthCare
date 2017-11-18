package com.fxlabs.fxt.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxlabs.fxt.cli.rest.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Fx server connection details


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
    private ProjectRestRepository projectRepository;
    @Autowired
    private EnvRestRepository envRepository;
    @Autowired
    private DataSetRestRepository dataSetRestRepository;
    @Autowired
    private JobRestRepository jobRestRepository;


    public FxCommandService() {

    }

    public void load() {
        try {
            // read fx server details


            // create project
            logger.info("loading project...");
            Project project = new Project();
            project.setName(projectName);

            project = projectRepository.save(project);
            logger.info("project created with id [{}]...", project.getId());

            // create env
            logger.info("loading env details...");

            ProjectEnvironment env = new ProjectEnvironment();
            env.setName(this.envName);
            env.setBaseUrl(envUrl);
            NameDto proj = new NameDto();
            proj.setId(project.getId());
            proj.setVersion(project.getVersion());
            env.setProject(proj);
            ProjectCredential cred = new ProjectCredential();
            cred.setName("Default");
            cred.setMethod(envAuthType);
            cred.setUsername(envUsername);
            cred.setPassword(envPassword);
            List<ProjectCredential> list = new ArrayList<>();
            list.add(cred);
            env.setCredentials(list);

            env = envRepository.save(env);
            logger.info("env created with id [{}]...", env.getId());

            // create dataset

            ObjectMapper mapper = new ObjectMapper();

            ProjectDataSet[] values = mapper.readValue(new File("dataset/data.json"), ProjectDataSet[].class);

            logger.info("ds size: [{}]", values.length);

            logger.info("ds : [{}]", values[0].toString());

            NameDto proj_ = new NameDto();
            proj_.setId(project.getId());
            proj_.setVersion(project.getVersion());

            for (ProjectDataSet dataSet : values) {
                dataSet.setProject(proj_);
                dataSetRestRepository.save(dataSet);
            }
            logger.info("Dataset uploaded...");

            // read job
            logger.info("loading job details...");
            ProjectJob job = new ProjectJob();
            job.setName(this.jobName);
            job.setProject(proj_);

            job.setDataSetTags(this.jobTags);

            job.setProjectEnvironment(env);

            job.setRegion(this.jobRegion);
            job = jobRestRepository.save(job);
            logger.info("job created with id [{}]...", job.getId());


            logger.info("Successful!");

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }

    }

    public void lsJobs() {
        List<ProjectJob> list = jobRestRepository.findAll();
        for (ProjectJob job : list) {
            System.out.println(job.getName() + " (" + job.getId() + ")");
        }
    }

    public void runJob(String jobId) {
        Run run = jobRestRepository.run(jobId);
        System.out.println("Run ID: " + run.getId());

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            run = jobRestRepository.findInstance(run.getId());
            if (StringUtils.pathEquals(run.getTask().getStatus(), "Completed!")) {
                printRun(run);
                break;
            }
        }

    }

    public void inspectRun(String id) {
        Run run = jobRestRepository.findInstance(id);
        printRun(run);

    }

    private void printRun(Run run) {
        RunTask task = run.getTask();
        System.out.println("Name: " + task.getName());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Total Tests: " + task.getTotalTests());
        System.out.println("Total Completed: " + task.getTotalTestCompleted());
        System.out.println("Total Failed: " + task.getFailedTests());
        System.out.println("Total Skipped: " + task.getSkippedTests());
        System.out.println("Start Time: " + task.getStartTime());
        System.out.println("End Time: " + task.getEndTime());
        System.out.println("Total Time : " + task.getTotalTime() + " ms");

    }

}