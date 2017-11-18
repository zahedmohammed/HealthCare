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
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

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
    @Autowired
    private RunRestRepository runRestRepository;


    public FxCommandService() {

    }

    public String load() {
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
            proj.setName(project.getName());
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
            proj_.setName(project.getName());
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

            printJobs(Arrays.asList(job));

            return job.getId();

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public void loadAndRun() {
        System.out.println ("loading data...");
        String jobId = load();
        System.out.println ("running job...");
        runJob(jobId);
    }
    public void lsJobs() {
        List<ProjectJob> list = jobRestRepository.findAll();

        printJobs(list);

    }

    public void lsProjects() {
        List<Project> list = projectRepository.findAll();

        printProjects(list);

    }

    public void lsRuns() {
        List<Run> list = runRestRepository.findAll();

        List<RunTask> tasks = new ArrayList<>();
        for (Run r : list) {
            tasks.add(r.getTask());
        }
        printRuns(tasks);

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
        printRuns(Arrays.asList(task));

        /*
        System.out.println("Name: " + task.getName());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Total Tests: " + task.getTotalTests());
        System.out.println("Total Completed: " + task.getTotalTestCompleted());
        System.out.println("Total Failed: " + task.getFailedTests());
        System.out.println("Total Skipped: " + task.getSkippedTests());
        System.out.println("Start Time: " + task.getStartTime());
        System.out.println("End Time: " + task.getEndTime());
        System.out.println("Total Time : " + task.getTotalTime() + " ms");
        */


    }

    private void printProjects(List<Project> list) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Project Name");
        header.put("id", "Project ID");

        // "name", "id", "project.name", "region"
        Table table = new TableBuilder(new BeanListTableModel<Project>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);
    }

    private void printRuns(List<RunTask> list) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Name");
        header.put("status", "Status");
        header.put("totalTests", "Total Suites");
        header.put("totalTestCompleted", "Total Completed");
        header.put("failedTests", "Total Failed");
        header.put("skippedTests", "Total Skipped");
        header.put("totalTime", "Total Time");

        // "name", "id", "project.name", "region"
        Table table = new TableBuilder(new BeanListTableModel<RunTask>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);
    }

    private void printJobs(List<ProjectJob> list) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Job Name");
        header.put("id", "Job ID");
        header.put("project.name", "Project Name");
        header.put("project.id", "Project ID");
        header.put("region", "Region");

        // "name", "id", "project.name", "region"
        Table table = new TableBuilder(new BeanListTableModel<ProjectJob>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);
    }

}