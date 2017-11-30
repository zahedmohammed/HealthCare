package com.fxlabs.fxt.cli.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.cli.beans.Config;
import com.fxlabs.fxt.cli.rest.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import org.apache.commons.io.FileUtils;
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
@PropertySource(ignoreResourceNotFound = true, value = "file:${user.home}/fxt/fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Fx server connection details

    @Autowired
    private ProjectRestRepository projectRepository;
    @Autowired
    private TestSuiteRestRepository dataSetRestRepository;
    @Autowired
    private RunRestRepository runRestRepository;


    public FxCommandService() {

    }

    public String load(String projectDir) {
        try {
            // read fx server details

            // create project

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            logger.info("loading project.yml...");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            Config config = yamlMapper.readValue(new File(projectDir + "project.yml"), Config.class);

            //System.out.println(config);

            Project project = new Project();
            project.setName(config.getName());
            project.setLicenses(config.getLicenses());
            project.setDescription(config.getDescription());


            // create env
            logger.info("loading env details...");

            List<Environment> environments = new ArrayList<>();
            for (com.fxlabs.fxt.cli.beans.Environment environment : config.getEnvironments()) {
                Environment env = new Environment();
                env.setName(environment.getName());
                env.setBaseUrl(environment.getBaseUrl());

                //env.setProject(proj);

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

                //env = envRepository.save(env);

                environments.add(env);

                logger.info("env created with id [{}]...", env.getId());
            }

            project.setEnvironments(environments);


            // read job
            List<com.fxlabs.fxt.dto.project.Job> jobs = new ArrayList<>();
            logger.info("loading job details...");
            for (com.fxlabs.fxt.cli.beans.Job jobProfile : config.getJobs()) {
                com.fxlabs.fxt.dto.project.Job job = new com.fxlabs.fxt.dto.project.Job();
                job.setName(jobProfile.getName());
                //job.setProject(proj);

                job.setTags(jobProfile.getTags());

                /*Environment projectEnvironment = null;
                for (Environment pe : projectEnvironments) {
                    if (pe.getName().equals(jobProfile.getEnvironment())) {
                        projectEnvironment = pe;
                    }
                }
                job.setEnvironment(projectEnvironment);*/
                job.setEnvironment(jobProfile.getEnvironment());

                job.setRegion(jobProfile.getRegion());
                //job = jobRestRepository.save(job);

                jobs.add(job);

                logger.info("job created with id [{}]...", job.getId());
            }

            project.setJobs(jobs);

            project = projectRepository.save(project);
            logger.info("project created with id [{}]...", project.getId());
            //System.out.println(project);

            NameDto proj = new NameDto();
            proj.setId(project.getId());
            proj.setName(project.getName());
            proj.setVersion(project.getVersion());

            com.fxlabs.fxt.dto.project.Job job_ = null;
            for (Job job : project.getJobs()) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(job.getName(), "Default")) {
                    job_ = job;
                    break;
                }
            }


            // create dataset
            System.out.println("");
            System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                    "Loading Test-Suites...",
                    AnsiColor.DEFAULT));



            File dataFolder = new File(projectDir + "test-suites");
            Collection<File> files = FileUtils.listFiles(dataFolder, new String[] {"yml", "yaml", "YML", "YAML"}, true);

            for (File file : files) {

                if (!StringUtils.endsWithIgnoreCase(file.getName(), ".yml")) {
                    System.out.println(AnsiOutput.toString(AnsiColor.RED,
                            String.format("%s [Skipped]", file.getName()),
                            AnsiColor.DEFAULT));
                    continue;
                }

                System.out.print(AnsiOutput.toString(AnsiColor.WHITE,
                        String.format("%s\r", file.getName()),
                        AnsiColor.DEFAULT));

                TestSuite testSuite = yamlMapper.readValue(file, TestSuite.class);
                //logger.info("ds size: [{}]", values.length);

                logger.info("ds : [{}]", testSuite.toString());

                if (StringUtils.isEmpty(testSuite.getName())) {
                    testSuite.setName(file.getName());
                }

                testSuite.setProject(proj);
                dataSetRestRepository.save(testSuite);

                System.out.print(AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("%s [OK]\n", file.getName()),
                        AnsiColor.DEFAULT));
            }


            logger.info("test-suites successfully uploaded...");


            logger.info("Successful!");

            //printJobs(jobs);

            return job_.getId();

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public void loadAndRun(String projectDir) {
        Date start = new Date();
        //System.out.println("loading data...");
        String jobId = load(projectDir);
        Date loadEnd = new Date();
        //System.out.println("running job...");
        runJob(jobId);

        System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT,
                        String.format("Test-Suite Load Time: %s ms \nJob Time: %s ms \nTotal Time: %s ms",
                                (new Date().getTime() - start.getTime()),
                                (loadEnd.getTime() - start.getTime()),
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

    }

    public void lsJobs() {
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
        Run run = runRestRepository.run(jobId);
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("Running job: %s", jobId),
                AnsiColor.DEFAULT));

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

        run = runRestRepository.findInstance(run.getId());
        printRun(run, "\n");

    }

    public void inspectRun(String id) {
        Run run = runRestRepository.findInstance(id);
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
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Run summary:",
                AnsiColor.DEFAULT));
        System.out.print(
                AnsiOutput.toString(AnsiColor.DEFAULT,
                        String.format("Run Id: %s \nStatus: %s \nTest-Suites Completed: %s \nTest Completed: %s \nTest Failed: %s \nTest Skipped: %s \nProcessing Time: %s ms%s",
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

    private void printJobs(List<com.fxlabs.fxt.dto.project.Job> list) {
        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        header.put("name", "Job Name");
        header.put("id", "Job ID");
        header.put("project.name", "Project Name");
        header.put("project.id", "Project ID");
        header.put("region", "Region");

        // "name", "id", "project.name", "region"
        Table table = new TableBuilder(new BeanListTableModel<com.fxlabs.fxt.dto.project.Job>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);
    }

}