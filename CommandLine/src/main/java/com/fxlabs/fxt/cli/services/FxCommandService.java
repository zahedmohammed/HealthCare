package com.fxlabs.fxt.cli.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.cli.beans.Config;
import com.fxlabs.fxt.cli.rest.ProjectRestRepository;
import com.fxlabs.fxt.cli.rest.RunRestRepository;
import com.fxlabs.fxt.cli.rest.TestSuiteRestRepository;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.dto.run.TaskStatus;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.PropertySource;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:${user.home}/fxt/fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Fx server connection details
    Set<TestSuiteResponse> dataSets = new HashSet<>();
    @Autowired
    private ProjectRestRepository projectRepository;
    @Autowired
    private TestSuiteRestRepository dataSetRestRepository;
    @Autowired
    private RunRestRepository runRestRepository;


    public FxCommandService() {

    }

    public String load(String projectDir, String jobName) {
        try {
            // read fx server details

            // create project

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            System.out.println("loading Fxfile...");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            Config config = yamlMapper.readValue(new File(projectDir + "Fxfile"), Config.class);

            //System.out.println(config);

            Date lastSync = null;
            Project project = getProject(config);
            if (project == null) {
                System.out.println(String.format("Project [%s] doesn't exists", config.getName()));
                project = createProject(config);
            } else {
                System.out.println(String.format("Project [%s] exists and last-synced on [%s]", config.getName(), project.getLastSync()));
                lastSync = project.getLastSync();
            }
            //System.out.println(project);

            ProjectMinimalDto proj = new ProjectMinimalDto();
            proj.setId(project.getId());
            proj.setName(project.getName());
            proj.setVersion(project.getVersion());

            com.fxlabs.fxt.dto.project.Job job_ = null;
            for (Job job : project.getJobs()) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(job.getName(), jobName)) {
                    job_ = job;
                    break;
                }
            }

            if (job_ == null) {
                System.out.println(AnsiOutput.toString(AnsiColor.RED,
                        "No job found with the name: " + jobName,
                        AnsiColor.DEFAULT));
                return null;
            }

            // create dataset
            loadSuites(projectDir, yamlMapper, proj, lastSync);

            project.setLastSync(new Date());
            updateProject(project);

            logger.info("Successful!");

            //printJobs(jobs);

            return job_.getId();

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
        return null;
    }

    private Project getProject(Config config) {
        Project project = projectRepository.findByName(config.getName());
        return project;
    }

    private Project createProject(Config config) {
        Project project = new Project();
        project.setName(config.getName());
        project.setLicenses(config.getLicenses());
        project.setDescription(config.getDescription());

        List<Environment> environments = getEnvironments(config);


        project.setEnvironments(environments);
        List<Job> jobs = getJobs(config);


        project.setJobs(jobs);

        project = projectRepository.save(project);
        logger.info("project created with id [{}]...", project.getId());
        return project;
    }

    private Project updateProject(Project project) {
        project = projectRepository.update(project);
        logger.info("project created with id [{}]...", project.getId());
        return project;
    }

    private List<Environment> getEnvironments(Config config) {
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
        return environments;
    }

    private List<Job> getJobs(Config config) {
        // read job
        List<Job> jobs = new ArrayList<>();
        logger.info("loading job details...");
        for (com.fxlabs.fxt.cli.beans.Job jobProfile : config.getJobs()) {
            Job job = new Job();
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

            job.setRegions(jobProfile.getRegions());
            //job = jobRestRepository.save(job);

            jobs.add(job);

            logger.info("job created with id [{}]...", job.getId());
        }
        return jobs;
    }

    private void loadSuites(String projectDir, ObjectMapper yamlMapper, ProjectMinimalDto proj, Date lastSync) {
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Loading Test-Suites:",
                AnsiColor.DEFAULT));


        File dataFolder = new File(projectDir + "test-suites");
        Collection<File> files = FileUtils.listFiles(dataFolder, new String[]{"yml", "yaml", "YML", "YAML"}, true);

        AtomicInteger totalFiles = new AtomicInteger(0);
        files.parallelStream().forEach(file -> {

            TestSuite testSuite = null;
            try {
                //System.out.println(String.format("File [%s] last-modified [%s] last-sync [%s]", file.getName(), new Date(file.lastModified()), lastSync));
                if (lastSync != null && new Date(file.lastModified()).before(lastSync)) {
                    System.out.println(AnsiOutput.toString(AnsiColor.WHITE,
                            String.format("Test-Suite: %s [Up-to-date]", file.getName()),
                            AnsiColor.DEFAULT));
                    return;
                }
                testSuite = yamlMapper.readValue(file, TestSuite.class);
            } catch (IOException e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(AnsiOutput.toString(AnsiColor.RED,
                        String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage()),
                        AnsiColor.DEFAULT));
                return;
            }
            //logger.info("ds size: [{}]", values.length);

            logger.info("ds : [{}]", testSuite.toString());

            if (StringUtils.isEmpty(testSuite.getName())) {
                testSuite.setName(FilenameUtils.getBaseName(file.getName()));
            }

            testSuite.setProject(proj);
            try {
                if (lastSync == null) {
                    dataSetRestRepository.save(testSuite);
                } else {
                    dataSetRestRepository.update(testSuite);
                }
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
            }

            System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                    String.format("Test-Suite: %s [Synced]", file.getName()),
                    AnsiColor.DEFAULT));

            totalFiles.incrementAndGet();
        });

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("\nTotal Suites Loaded: [%s]", totalFiles),
                AnsiColor.DEFAULT));
        logger.info("test-suites successfully uploaded...");
    }

    public void loadAndRun(String projectDir, String jobName, String region, String tags, String envName) {
        Date start = new Date();
        //System.out.println("loading data...");
        if (StringUtils.isEmpty(projectDir)) {
            File file = new File(".");
            projectDir = file.getAbsolutePath();
        }
        String jobId = load(projectDir, jobName);
        Date loadEnd = new Date();
        //System.out.println("running job...");
        dataSets = new HashSet<>();
        runJob(jobId, region, tags, envName);

        System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT,
                        String.format("Total Time: %s ms",
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

        printFailedSuites(dataSets);

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


    public void runJob(String jobId, String region, String tags, String envName) {
        Run run = runRestRepository.run(jobId, region, tags, envName);
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("Running Job: %s Run: %s", jobId, run.getId()),
                AnsiColor.DEFAULT));

        int page = 0;
        int pageSize = 10;
        int count = 0;
        boolean isComplete = false;

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

            if (isComplete) break;

            run = runRestRepository.findInstance(run.getId());
            if (run.getTask().getStatus() == TaskStatus.COMPLETED || run.getTask().getStatus() == TaskStatus.FAIL) {
                isComplete = true;
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
                        String.format("Run Id: %s " +
                                        "\nStatus: %s " +
                                        "\nDescription: %s" +
                                        "\nTotal Test-Suites: %s " +
                                        "\nTotal Test: %s " +
                                        "\nTotal Failed: %s " +
                                        "\nProcessing Time: %s ms%s",
                                run.getId(),
                                run.getTask().getStatus(),
                                run.getTask().getDescription(),
                                run.getTask().getTotalTests(),
                                run.getTask().getTotalTestCompleted(),
                                run.getTask().getFailedTests(),
                                run.getTask().getTotalTime(),
                                carriageReturn)
                        , AnsiColor.DEFAULT)
        );
    }

    private void printFailedSuites(Set<TestSuiteResponse> dataSets) {
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "\nExecution logs:",
                AnsiColor.DEFAULT));
        dataSets.forEach(suite -> {
            if (!org.apache.commons.lang3.StringUtils.equalsIgnoreCase(suite.getStatus(), "pass") &&
                    !StringUtils.isEmpty(suite.getLogs())) {
                System.out.println(suite.getTestSuite());
                printErrorLogs(suite.getLogs());
            }
        });
    }

    private void printErrorLogs(String logs) {
        System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT, logs, AnsiColor.DEFAULT)
        );
    }

    private void printDataSet(TestSuiteResponse ds) {
        if (ds.getTotalFailed() > 0) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("Test-Suite: %s, Pass: %s, Fail: [%s], Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getRequestTime())
                            , AnsiColor.DEFAULT)
            );
        } else {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("Test-Suite: %s, Pass: [%s], Fail: %s, Time: %s ms",
                                    ds.getTestSuite(), ds.getTotalPassed(), ds.getTotalFailed(), ds.getRequestTime())
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