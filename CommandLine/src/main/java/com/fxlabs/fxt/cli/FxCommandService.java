package com.fxlabs.fxt.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.cli.beans.Config;
import com.fxlabs.fxt.cli.beans.JobProfile;
import com.fxlabs.fxt.cli.rest.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
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

            List<Environment> projectEnvironments = new ArrayList<>();
            for (com.fxlabs.fxt.cli.beans.Environment environment : config.getTestApp().getEnvironments()) {
                Environment env = new Environment();
                env.setName(environment.getName());
                env.setBaseUrl(environment.getBaseUrl());

                env.setProject(proj);

                List<Auth> list = new ArrayList<>();
                for (com.fxlabs.fxt.cli.beans.Auth credential : environment.getAuths()) {
                    Auth cred = new Auth();
                    cred.setName(credential.getName());
                    cred.setAuthType(credential.getAuthType());
                    cred.setUsername(credential.getUsername());
                    cred.setPassword(credential.getPassword());

                    list.add(cred);
                }
                env.setAuths(list);

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
                            String.format("%s [Skipped]", file.getName()),
                            AnsiColor.DEFAULT));
                    continue;
                }

                System.out.print(AnsiOutput.toString(AnsiColor.WHITE,
                        String.format("%s\r", file.getName()),
                        AnsiColor.DEFAULT));

                TestSuite projectDataSet = yamlMapper.readValue(file, TestSuite.class);
                //logger.info("ds size: [{}]", values.length);

                logger.info("ds : [{}]", projectDataSet.toString());

                if (StringUtils.isEmpty(projectDataSet.getName())) {
                    projectDataSet.setName(file.getName());
                }
                projectDataSet.setProject(proj);
                dataSetRestRepository.save(projectDataSet);

                System.out.print(AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("%s [OK]\n", file.getName()),
                        AnsiColor.DEFAULT));
            }


            logger.info("test-suites successfully uploaded...");

            // read job
            Job job_ = null;
            List<Job> jobs = new ArrayList<>();
            logger.info("loading job details...");
            for (JobProfile jobProfile : config.getTestApp().getJobProfiles()) {
                Job job = new Job();
                job.setName(jobProfile.getName());
                job.setProject(proj);

                job.setTags(jobProfile.getTags());

                Environment projectEnvironment = null;
                for (Environment pe : projectEnvironments) {
                    if (pe.getName().equals(jobProfile.getEnvironment())) {
                        projectEnvironment = pe;
                    }
                }
                job.setEnvironment(projectEnvironment);

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
        Date start = new Date();
        //System.out.println("loading data...");
        String jobId = load();
        Date loadEnd = new Date();
        //System.out.println("running job...");
        runJob(jobId);

        System.out.println(
                AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,
                        String.format("\nTotal time: %s ms, Load Time :%s, Job Time: %s",
                                (new Date().getTime() - start.getTime()),
                                (loadEnd.getTime() - start.getTime()),
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

    }

    public void lsJobs() {
        List<Job> list = jobRestRepository.findAll();

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
        Set<TestSuiteResponse> dataSets = new HashSet<>();
        int count = 0;

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // find DataSets
            // add them to set and print

            Response<List<TestSuiteResponse>> response = runRestRepository.findTestSuitesByRunId(run.getId(), page, pageSize);

            for (TestSuiteResponse ds : response.getData()) {
                if (dataSets.add(ds)) {
                    printDataSet(ds);
                }
            }

            //System.out.println (run.getId() + " " + page + " " + pageSize + " " + response.getTotalElements());

            if (response.getTotalElements() < run.getTask().getTotalTests()) {
                if (response.getTotalElements() > ((page + 1) * pageSize)) {
                    page++;
                    count = 0;
                } else {
                    if (count++ > 5) page = 0;
                }
            } else {
                break;
            }
        }

        run = jobRestRepository.findInstance(run.getId());
        printRun(run, "\n");

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
                AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,
                        String.format("\nID: %s, Status: %s, Suites: [%s], Completed: [%s], Failed: [%s], Skipped: %s, Time: [%s] ms%s\n",
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

    private void printDataSet(TestSuiteResponse ds) {
        if (ds.getTotalFailed() > 0) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("Test-Suite: %s, Pass: %s, Fail: [%s], Skip: %s, Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getTotalSkipped(),
                                    ds.getRequestTime())
                            , AnsiColor.DEFAULT)
            );
        } else {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("Test-Suite: %s, Pass: [%s], Fail: %s, Skip: %s, Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getTotalSkipped(),
                                    ds.getRequestTime())
                            , AnsiColor.DEFAULT)
            );
        }
    }

    private void printJobs(List<Job> list) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Job Name");
        header.put("id", "Job ID");
        header.put("project.name", "Project Name");
        header.put("project.id", "Project ID");
        header.put("region", "Region");

        // "name", "id", "project.name", "region"
        Table table = new TableBuilder(new BeanListTableModel<Job>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);
    }

}