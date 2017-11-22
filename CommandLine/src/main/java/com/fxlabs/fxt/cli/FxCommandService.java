package com.fxlabs.fxt.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.cli.beans.Config;
import com.fxlabs.fxt.cli.beans.Credential;
import com.fxlabs.fxt.cli.beans.Environment;
import com.fxlabs.fxt.cli.beans.JobProfile;
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
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Fx server connection details

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

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            logger.info("loading project.yml...");
            Config config = yamlMapper.readValue(new File("project.yml"), Config.class);

            //System.out.println(config);

            Project project = new Project();
            project.setName(config.getTestApp().getName());

            project = projectRepository.save(project);
            logger.info("project created with id [{}]...", project.getId());

            // create env
            logger.info("loading env details...");

            NameDto proj = new NameDto();
            proj.setId(project.getId());
            proj.setName(project.getName());
            proj.setVersion(project.getVersion());

            List<ProjectEnvironment> projectEnvironments = new ArrayList<>();
            for (Environment environment : config.getTestApp().getEnvironments()) {
                ProjectEnvironment env = new ProjectEnvironment();
                env.setName(environment.getName());
                env.setBaseUrl(environment.getBaseUrl());

                env.setProject(proj);

                List<ProjectCredential> list = new ArrayList<>();
                for (Credential credential : environment.getCredentials()) {
                    ProjectCredential cred = new ProjectCredential();
                    cred.setName(credential.getName());
                    cred.setMethod(credential.getAuthType());
                    cred.setUsername(credential.getUsername());
                    cred.setPassword(credential.getPassword());

                    list.add(cred);
                }
                env.setCredentials(list);

                env = envRepository.save(env);

                projectEnvironments.add(env);

                logger.info("env created with id [{}]...", env.getId());
            }


            // create dataset

            ObjectMapper mapper = new ObjectMapper();


            File dataFolder = new File("dataset");

            for (File file : dataFolder.listFiles()) {

                if (!StringUtils.endsWithIgnoreCase(file.getName(), ".yml")) {
                    continue;
                }

                System.out.println(String.format("loading %s", file.getName()));

                ProjectDataSet values = yamlMapper.readValue(file, ProjectDataSet.class);
                //logger.info("ds size: [{}]", values.length);

                logger.info("ds : [{}]", values.toString());


                //for (ProjectDataSet dataSet : values) {
                values.setProject(proj);
                //}
                dataSetRestRepository.save(values);
            }


            logger.info("Dataset uploaded...");

            // read job
            ProjectJob job_ = null;
            List<ProjectJob> jobs = new ArrayList<>();
            logger.info("loading job details...");
            for (JobProfile jobProfile : config.getTestApp().getJobProfiles()) {
                ProjectJob job = new ProjectJob();
                job.setName(jobProfile.getName());
                job.setProject(proj);

                job.setDataSetTags(jobProfile.getTags());

                ProjectEnvironment projectEnvironment = null;
                for (ProjectEnvironment pe : projectEnvironments) {
                    if (pe.getName().equals(jobProfile.getEnvironment())) {
                        projectEnvironment = pe;
                    }
                }
                job.setProjectEnvironment(projectEnvironment);

                job.setRegion(jobProfile.getRegion());
                job = jobRestRepository.save(job);

                jobs.add(job);

                if (job.getName().equals("Default")) {
                    job_ = job;
                }

                logger.info("job created with id [{}]...", job.getId());
            }

            if (job_ == null) {
                job_ = jobs.get(0);
            }


            logger.info("Successful!");

            //printJobs(jobs);

            return job_.getId();

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public void loadAndRun() {
        //System.out.println("loading data...");
        String jobId = load();
        //System.out.println("running job...");
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
        //printRuns(tasks);

    }

    public void runJob(String jobId) {
        Run run = jobRestRepository.run(jobId);
        System.out.println("Running Job : " + run.getId());
        System.out.println ("");

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            run = jobRestRepository.findInstance(run.getId());
            if (StringUtils.pathEquals(run.getTask().getStatus(), "Completed!")) {
                break;
            } else {
                printRun(run);
            }
        }

    }

    public void inspectRun(String id) {
        Run run = jobRestRepository.findInstance(id);
        printRun(run);

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

    private void printRun(Run run) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Name");
        header.put("status", "Status");
        header.put("totalTests", "Total Suites");
        header.put("totalTestCompleted", "Total Completed");
        header.put("failedTests", "Total Failed");
        header.put("skippedTests", "Total Skipped");
        header.put("totalTime", "Total Time");

        // "name", "id", "project.name", "region"
        /*Table table = new TableBuilder(new BeanListTableModel<RunTask>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);*/
        //System.out.print(result);
        System.out.print(String.format("ID: %s, Status: %s, Suites: %s, Completed: %s, Failed: %s, Skipped: %s, Time: %s ms\r",
                run.getId(), run.getTask().getStatus(), run.getTask().getTotalTests(), run.getTask().getTotalTestCompleted(),
                run.getTask().getFailedTests(), run.getTask().getSkippedTests(), run.getTask().getTotalTime()));
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