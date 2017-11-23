package com.fxlabs.fxt.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.cli.beans.Config;
import com.fxlabs.fxt.cli.beans.Auth;
import com.fxlabs.fxt.cli.beans.Environment;
import com.fxlabs.fxt.cli.beans.JobProfile;
import com.fxlabs.fxt.cli.rest.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.DataSet;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.PropertySource;
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
                for (Auth credential : environment.getAuths()) {
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


            File dataFolder = new File("test-suites");

            for (File file : dataFolder.listFiles()) {

                if (!StringUtils.endsWithIgnoreCase(file.getName(), ".yml")) {
                    System.out.println(AnsiOutput.toString(AnsiColor.RED,
                            String.format("%s skipped...", file.getName()),
                            AnsiColor.DEFAULT));
                    continue;
                }


                ProjectDataSet projectDataSet = yamlMapper.readValue(file, ProjectDataSet.class);
                //logger.info("ds size: [{}]", values.length);

                logger.info("ds : [{}]", projectDataSet.toString());

                if (StringUtils.isEmpty(projectDataSet.getName())) {
                    projectDataSet.setName(file.getName());
                }
                projectDataSet.setProject(proj);
                dataSetRestRepository.save(projectDataSet);

                System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("%s loaded...", file.getName()),
                        AnsiColor.DEFAULT));
            }


            logger.info("test-suites uploaded...");

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
        System.out.println("");
        System.out.println("Running Job : " + run.getId());
        System.out.println("");

        int page = 0;
        int pageSize = 10;
        Set<DataSet> dataSets = new HashSet<>();

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // find DataSets
            // add them to set and print
            //System.out.println ("" + page + " " + pageSize);
            Response<List<DataSet>> response = runRestRepository.findTestSuitesByRunId(run.getId(), page, pageSize);

            for (DataSet ds : response.getData()) {
                if (dataSets.add(ds)) {
                    printDataSet(ds);
                }
            }

            if (((page + 1) * response.getTotalElements()) < run.getTask().getTotalTests()) {
                if (response.getTotalElements() >= pageSize) page++;
            } else {
                break;
            }
        }

        run = jobRestRepository.findInstance(run.getId());
        //if (StringUtils.pathEquals(run.getTask().getStatus(), "Completed!")) {
        printRun(run, "\n");
        //    break;
        //} else {
        //    printRun(run, "\r");
        //}

    }

    public void inspectRun(String id) {
        Run run = jobRestRepository.findInstance(id);
        //printRun(run);

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

    private void printRun(Run run, String carriageReturn) {
        System.out.print(
                AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("ID: %s, Status: %s, Suites: %s, Completed: %s, Failed: %s, Skipped: %s, Time: %s ms%s",
                                run.getId(), run.getTask().getStatus(), run.getTask().getTotalTests(), run.getTask().getTotalTestCompleted(),
                                run.getTask().getFailedTests(), run.getTask().getSkippedTests(), run.getTask().getTotalTime(), carriageReturn)
                        , AnsiColor.DEFAULT)
        );
    }

    private void printErrorLogs(List<String> logs) {
        System.out.println("\n Error logs....\n");
        for (String log : logs) {
        }
    }

    private void printDataSet(DataSet ds) {
        if (ds.getTotalFailed() > 0) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("Test-Suite: %s, Pass: %s, Fail: %s, Skip: %s, Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getTotalSkipped(),
                                    ds.getRequestTime())
                            , AnsiColor.DEFAULT)
            );
        } else {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("Test-Suite: %s, Pass: %s, Fail: %s, Skip: %s, Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getTotalSkipped(),
                                    ds.getRequestTime())
                            , AnsiColor.DEFAULT)
            );
        }
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