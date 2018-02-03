package com.fxlabs.fxt.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.sdk.beans.Config;
import com.fxlabs.fxt.sdk.rest.*;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.dto.run.TaskStatus;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import org.apache.commons.codec.digest.DigestUtils;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:${user.home}/fxt/fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    // Fx server connection details
    Set<TestSuiteResponse> dataSets = new HashSet<>();
    @Autowired
    private ProjectRestRepository projectRepository;
    @Autowired
    private TestSuiteRestRepository testSuiteRestRepository;
    @Autowired
    private RunRestRepository runRestRepository;
    @Autowired
    private JobRestRepository jobRestRepository;
    @Autowired
    private EnvRestRepository envRestRepository;

    public void loadAndRun(String projectDir, String jobName, String region, String tags, String envName, String suites) {
        Date start = new Date();
        //System.out.println("loading data...");
        if (StringUtils.isEmpty(projectDir)) {
            File file = new File(".");
            projectDir = file.getAbsolutePath();
        }
        Project project = load(projectDir);

        String jobId = locateJobId(jobName, project);

        Date loadEnd = new Date();

        //System.out.println("running job...");
        dataSets = new HashSet<>();
        runJob(jobId, region, tags, envName, suites);

        System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT,
                        String.format("Total Time: %s ms",
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

        printFailedSuites(dataSets);

    }

    /**
     * Loads Project
     * Loads Test-Suites
     *
     * @param projectDir
     * @return
     */
    public Project load(String projectDir) {
        try {
            // read fx server details

            // create project

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            System.out.println("loading Fxfile...");
            taskLogger.get().append("loading Fxfile...").append("\n");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File fxfile = FileUtils.getFile(new File(projectDir), "Fxfile.yaml");

            //new File(projectDir + "Fxfile.yml")
            Config config = yamlMapper.readValue(fxfile, Config.class);

            //System.out.println(config);

            Date lastSync = null;
            List<ProjectFile> projectFiles = null;
            Project project = getProjectByName(config);

            if (project == null) {
                System.out.println(String.format("Fxfile.yaml project [%s] not found...creating new", config.getName()));
                taskLogger.get().append(String.format("Fxfile.yaml project [%s] not found...creating new", config.getName())).append("\n");
                Response<Project> projectResponse = createProject(config);

                if (projectResponse.isErrors()) {
                    System.err.println(projectResponse.getMessages());
                    return null;
                }

                project = projectResponse.getData();

                saveEnvs(config, project.getId());

                saveJobs(config, project.getId());

                System.out.println(String.format("Project created with id: [%s]", project.getId()));
                taskLogger.get().append(String.format("Project created with id: [%s]", project.getId())).append("\n");
            } else {
                System.out.println(String.format("Fxfile.yaml project [%s] exists and last-synced date [%s]", config.getName(), project.getLastSync()));
                taskLogger.get().append(String.format("Fxfile.yaml project [%s] exists and last-synced date [%s]", config.getName(), project.getLastSync())).append("\n");

                projectFiles = getProjectChecksums(project.getId());

                String checksum = null;
                try {
                    String fxfileContent = FileUtils.readFileToString(fxfile, "UTF-8");
                    checksum = DigestUtils.md5Hex(fxfileContent);
                } catch (IOException e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(String.format("Failed loading [%s] file content with error [%s]", fxfile.getName(), e.getLocalizedMessage()));
                    taskLogger.get().append(String.format("Failed loading [%s] file content with error [%s]", fxfile.getName(), e.getLocalizedMessage())).append("\n");
                }

                //System.out.println(projectFiles);
                //System.out.println(checksum);

                if (!isChecksumPresent(projectFiles, fxfile, checksum)) {

                    Response<Project> projectResponse = updateProject(project, config, fxfile, checksum);

                    if (projectResponse.isErrors()) {
                        System.err.println(projectResponse.getMessages());
                    }
                    System.out.println(String.format("Project id: [%s]", projectResponse.getData().getId()));
                    taskLogger.get().append(String.format("Project id: [%s]", projectResponse.getData().getId())).append("\n");
                    System.out.println("Project updated...");
                    taskLogger.get().append("Project updated...").append("\n");
                }

            }

            // create dataset

            loadSuites(projectDir, yamlMapper, project.getId(), projectFiles);

            return project;

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
            taskLogger.get().append(String.format("Failed with error [%s]", e.getLocalizedMessage())).append("\n");
        }
        return null;
    }

    private Project getProjectByName(Config config) {
        Project project = projectRepository.findByName(config.getName());
        return project;
    }

    private Response<Project> createProject(Config config) {
        Project project = new Project();
        project.setName(config.getName());
        project.setLicenses(config.getLicenses());
        project.setDescription(config.getDescription());
        Response<Project> projectResponse = projectRepository.save(project);

        logger.info("project created with id [{}]...", project.getId());
        taskLogger.get().append(String.format("project created with id [%s]...", project.getId())).append("\n");
        return projectResponse;
    }

    private Response<List<Environment>> saveEnvs(Config config, String projectId) {
        List<Environment> environments = extractEnvironments(config, projectId);
        return envRestRepository.saveAll(environments);
    }

    private Response<List<Job>> saveJobs(Config config, String projectId) {
        List<Job> jobs = extractJobs(config, projectId);
        return this.jobRestRepository.saveAll(jobs);
    }

    private Response<Project> updateProject(Project project, Config config, File fxfile, String checksum) {

        project.setLicenses(config.getLicenses());
        project.setDescription(config.getDescription());

        String fxfileContent = null;
        try {
            FileUtils.readFileToString(fxfile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long lastModified = fxfile.lastModified();

        project.getProps().put(Project.FILE_CONTENT, fxfileContent);
        project.getProps().put(Project.MODIFIED_DATE, String.valueOf(lastModified));
        project.getProps().put(Project.FILE_NAME, fxfile.getName());
        project.getProps().put(Project.MD5_HEX, checksum);

        //project.setLastSync(new Date());

        Response<Project> projectResponse = projectRepository.update(project);

        //System.out.println(project);
        mergeAndSaveEnvs(config, project.getId());

        mergeAndSaveJobs(config, project.getId());


        logger.info("project updated with id [{}]...", project.getId());
        taskLogger.get().append("Project updated...").append("\n");
        return projectResponse;
    }

    private Response<List<Environment>> mergeAndSaveEnvs(Config config, String projectId) {
        List<Environment> environments = extractEnvironments(config, projectId);
        Response<List<Environment>> olds = envRestRepository.findByProjectId(projectId);
        List<Environment> oldEnvs = new ArrayList<>();

        if (olds.isErrors()) {
            System.err.println(olds.getMessages());
        } else if (!CollectionUtils.isEmpty(olds.getData())) {
            oldEnvs.addAll(olds.getData());
        }
        for (Environment environment : environments) {
            boolean found = false;
            for (Environment old : oldEnvs) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(environment.getName(), old.getRefId())) {
                    old.setDeleted(environment.isDeleted());
                    old.setName(environment.getName());
                    old.setDescription(environment.getDescription());
                    old.setBaseUrl(environment.getBaseUrl());

                    List<Auth> list = new ArrayList<>();
                    for (Auth credential : environment.getAuths()) {
                        boolean authFound = false;
                        for (Auth a : old.getAuths()) {
                            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(credential.getName(), a.getName())) {
                                // update
                                a.setAuthType(credential.getAuthType());
                                a.setUsername(credential.getUsername());
                                a.setPassword(credential.getPassword());
                                authFound = true;
                                break;
                            }
                        }
                        if (!authFound) {
                            // new auth
                            Auth cred = new Auth();
                            cred.setName(credential.getName());
                            cred.setAuthType(credential.getAuthType());
                            cred.setUsername(credential.getUsername());
                            cred.setPassword(credential.getPassword());
                            old.getAuths().add(cred);
                        }

                    }
                    //old.setAuths(list);


                    found = true;
                    break;
                }
            }
            if (!found) {
                Environment e = new Environment();
                e = environment;
                oldEnvs.add(e);
            }
        }
        Response<List<Environment>> response = envRestRepository.saveAll(oldEnvs);
        System.out.println("Env updated...");
        taskLogger.get().append("Environments updated...").append("\n");
        return response;
    }

    private Response<List<Job>> mergeAndSaveJobs(Config config, String projectId) {
        List<Job> jobs = extractJobs(config, projectId);
        Response<List<Job>> oldJobsResponse = this.jobRestRepository.findByProjectId(projectId);

        List<Job> oldJobs = new ArrayList<>();

        if (oldJobsResponse.isErrors()) {
            System.err.println(oldJobsResponse.getMessages());
        } else if (!CollectionUtils.isEmpty(oldJobsResponse.getData())) {
            oldJobs.addAll(oldJobsResponse.getData());
        }

        for (Job job : jobs) {
            boolean found = false;
            for (Job old : oldJobs) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(job.getName(), old.getRefId())) {
                    old.setTags(job.getTags());
                    old.setEnvironment(job.getEnvironment());
                    old.setDescription(job.getDescription());
                    old.setName(job.getName());
                    old.setRegions(job.getRegions());
                    found = true;
                    break;
                }
            }
            if (!found) {
                Job j = new Job();
                j = job;
                oldJobs.add(j);
            }
        }
        Response<List<Job>> listResponse = jobRestRepository.saveAll(oldJobs);
        taskLogger.get().append("Jobs updated...").append("\n");
        return listResponse;
    }

    private List<Environment> extractEnvironments(Config config, String projectId) {
        // create env
        logger.info("loading env details...");

        List<Environment> environments = new ArrayList<>();
        for (com.fxlabs.fxt.sdk.beans.Environment environment : config.getEnvironments()) {
            Environment env = new Environment();
            env.setName(environment.getName());
            env.setDeleted(environment.isInactive());
            env.setBaseUrl(environment.getBaseUrl());
            env.setDescription(environment.getDescription());
            env.setProjectId(projectId);

            //env.setProject(proj);

            List<Auth> list = new ArrayList<>();
            for (com.fxlabs.fxt.sdk.beans.Auth credential : environment.getAuths()) {
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

    private List<Job> extractJobs(Config config, String projectId) {
        // read job
        List<Job> jobs = new ArrayList<>();
        logger.info("loading job details...");
        for (com.fxlabs.fxt.sdk.beans.Job jobProfile : config.getJobs()) {
            Job job = new Job();
            job.setName(jobProfile.getName());

            job.setTags(jobProfile.getTags());

            job.setEnvironment(jobProfile.getEnvironment());

            job.setRegions(jobProfile.getRegions());
            job.setProjectId(projectId);

            jobs.add(job);

            logger.info("job created with id [{}]...", job.getId());
        }
        return jobs;
    }

    private void loadSuites(String projectDir, ObjectMapper yamlMapper, String projectId, List<ProjectFile> projectFiles) {
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Loading Test-Suites:",
                AnsiColor.DEFAULT));


        File dataFolder = new File(projectDir + "test-suites");
        Collection<File> files = FileUtils.listFiles(dataFolder, new String[]{"yaml"}, true);

        AtomicInteger totalFiles = new AtomicInteger(0);
        files.parallelStream().forEach(file -> {

            TestSuite testSuite = null;
            String testSuiteContent = null;
            final String checksum;
            try {
                testSuiteContent = FileUtils.readFileToString(file, "UTF-8");
            } catch (IOException e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage()));
                taskLogger.get().append(String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage())).append("\n");
            }

            try {
                //System.out.println(String.format("File [%s] last-modified [%s] last-sync [%s]", file.getName(), new Date(file.lastModified()), lastSync));
                checksum = DigestUtils.md5Hex(testSuiteContent);

                if (isChecksumPresent(projectFiles, file, checksum)) return;

                testSuite = yamlMapper.readValue(file, TestSuite.class);
            } catch (IOException e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(AnsiOutput.toString(AnsiColor.RED,
                        String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage()),
                        AnsiColor.DEFAULT));
                taskLogger.get().append(String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage())).append("\n");
                return;
            }
            //logger.info("ds size: [{}]", values.length);


            if (StringUtils.isEmpty(testSuite.getName())) {
                testSuite.setName(FilenameUtils.getBaseName(file.getName()));
            }

            // set file content

            Long lastModified = file.lastModified();
            testSuite.setProps(new HashMap<>());
            testSuite.getProps().put(Project.FILE_CONTENT, testSuiteContent);
            testSuite.getProps().put(Project.MODIFIED_DATE, String.valueOf(lastModified));
            testSuite.getProps().put(Project.MD5_HEX, checksum);
            testSuite.getProps().put(Project.FILE_NAME, file.getName());


            testSuite.setProjectId(projectId);
            try {
                testSuiteRestRepository.save(testSuite);
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
                taskLogger.get().append(String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage())).append("\n");
            }

            System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                    String.format("Test-Suite: %s [Synced]", file.getName()),
                    AnsiColor.DEFAULT));
            taskLogger.get().append(String.format("Test-Suite: %s [Synced]", file.getName())).append("\n");

            totalFiles.incrementAndGet();
        });

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("\nTotal Suites Loaded: [%s]", totalFiles),
                AnsiColor.DEFAULT));
        logger.info("test-suites successfully uploaded...");
        taskLogger.get().append("test-suites successfully uploaded...").append("\n");
    }

    private boolean isChecksumPresent(List<ProjectFile> projectFiles, File file, String checksum) {
        if (projectFiles != null && !CollectionUtils.isEmpty(projectFiles)) {
            Optional<ProjectFile> projectFileOptional = projectFiles.stream().filter(pf -> org.apache.commons.lang3.StringUtils.equals(checksum, pf.getChecksum()))
                    .findFirst();

            if (projectFileOptional.isPresent()) {
                System.out.println(AnsiOutput.toString(AnsiColor.WHITE,
                        String.format("Test-Suite: %s [Up-to-date]", file.getName()),
                        AnsiColor.DEFAULT));
                taskLogger.get().append(String.format("Test-Suite: %s [Up-to-date]", file.getName())).append("\n");
                return true;
            }
        }
        return false;
    }

    private List<ProjectFile> getProjectChecksums(String projectId) {
        final Response<List<ProjectFile>> projectFilesResponse = this.projectRepository.findProjectChecksums(projectId);
        return projectFilesResponse.getData();
    }

    private void lsJobs() {
    }

    private void lsProjects() {
        Response<List<Project>> list = projectRepository.findAll();

        if (list.isErrors()) {
            System.err.println(list.getMessages());
        }
        printProjects(list.getData());

    }

    private void lsRuns() {
        Response<List<Run>> list = runRestRepository.findAll();
        if (list.isErrors()) {
            System.err.println(list.getMessages());
        }

        List<RunTask> tasks = new ArrayList<>();
        for (Run r : list.getData()) {
            tasks.add(r.getTask());
        }
        //printRuns(tasks);

    }

    private void runJob(String jobId, String region, String tags, String envName, String suites) {
        Run run = runRestRepository.run(jobId, region, tags, envName, suites);
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

            //System.out.println (run.getId() + " Total Tests: " + run.getTask().getTotalTests() + " Completed: " + response.getTotalElements());

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

    private void inspectRun(String id) {
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

    private String locateJobId(String jobName, Project project) {
        Job job_ = null;
        Response<List<Job>> jobs = jobRestRepository.findByProjectId(project.getId());
        if (jobs.isErrors()) {
            System.err.println(jobs.getMessages());
        }
        for (Job job : jobs.getData()) {
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


        logger.info("Successful!");

        //printJobs(jobs);

        return job_.getId();
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