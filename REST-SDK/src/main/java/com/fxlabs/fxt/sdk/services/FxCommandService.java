package com.fxlabs.fxt.sdk.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.*;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.sdk.beans.Config;
import com.fxlabs.fxt.sdk.rest.*;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:/fx-sdk.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:/opt/fx/fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:/var/fx/fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:\\C:\\opt\\fx\\fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:${user.home}/fx/fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${fx_url:#{null}}")
    protected String url;
    @Value("${fx_username:#{null}}")
    protected String username;
    @Value("${fx_password:#{null}}")
    protected String password;

    // Fx server connection details
    protected Set<TestSuiteResponse> dataSets = new HashSet<>();
    protected Set<Suite> suiteSet = new HashSet<>();

    @Autowired
    private UsersRestRepository usersRestRepository;
    @Autowired
    private OrgRestRepository orgRestRepository;
    @Autowired
    private ProjectRestRepository projectRestRepository;
    @Autowired
    private TestSuiteRestRepository testSuiteRestRepository;
    @Autowired
    private DataSetRestRepository dataSetRestRepository;
    @Autowired
    private DataRecordRestRepository dataRecordRestRepository;
    @Autowired
    private RunRestRepository runRestRepository;
    @Autowired
    private JobRestRepository jobRestRepository;
    @Autowired
    private EnvRestRepository envRestRepository;

    private Map<Long, String> logFiles = new ConcurrentHashMap<>();
    private Long LAST_RUN_ID = 0L;

    public Response<OrgUsers> login() {
        return this.orgRestRepository.loginStatus();
    }

    public void loadAndRun(String projectDir, String projectName, String jobName, String region, String tags, String envName, String suites) {
        Date start = new Date();
        //System.out.println("loading data...");
        if (StringUtils.isEmpty(projectDir)) {
            File file = new File(".");
            projectDir = file.getAbsolutePath();
        }

        if (StringUtils.isEmpty(projectName)) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("Invalid project %s", projectName)
                            , AnsiColor.DEFAULT)
            );
            return;
        }

        if (StringUtils.isEmpty(jobName)) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("Invalid job %s", jobName)
                            , AnsiColor.DEFAULT)
            );
            return;
        }

        System.out.println(String.format("Locating project %s...", projectName));
        Response<Project> response = projectRestRepository.findByOrgAndName(projectName);
        if (response == null || response.isErrors()) {
            for (Message m : response.getMessages())
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                m.getValue()
                                , AnsiColor.DEFAULT)
                );
            return;
        }

        Project project = load(projectDir, response.getData().getId());

        if (project == null) {
            return;
        }

        String jobId = locateJobId(jobName, project);

        System.out.println("Test-Suite sync done!");

        Date loadEnd = new Date();

        System.out.println("Starting Job run...");
        dataSets = new HashSet<>();
        suiteSet = new HashSet<>();
        Long runId = runJob(jobId, region, tags, envName, suites);

        System.out.println(
                AnsiOutput.toString(AnsiColor.GREEN,
                        String.format(" Actual Processing Time: %s ms",
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

        String file = RandomStringUtils.randomAlphanumeric(6);
        File f = new File(file);

        System.out.println(
                AnsiOutput.toString(AnsiColor.GREEN,
                        String.format(" Log file: %s",
                                f.getAbsolutePath())
                        , AnsiColor.DEFAULT)
        );

        logFiles.put(runId, f.getAbsolutePath());
        LAST_RUN_ID = runId;

        System.out.println("");

        printFailedSuites(dataSets, f);

        //printSuiteHeader();

        //printSuites(suiteSet);


    }

    public void print(Long runId) {
        if (runId == null || runId == 0L) {
            runId = LAST_RUN_ID;
        }

        String file = logFiles.get(runId);
        if (StringUtils.isEmpty(file)) {
            System.out.println(String.format("No log file found for run_id %s", runId));
        }
        System.out.println("");
        try {
            FileUtils.readLines(new File(file), Charset.defaultCharset()).forEach(line -> {
                System.out.println(line);
            });
            System.out.println("");
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Loads Project
     * Loads Test-Suites
     *
     * @param projectDir
     * @return
     */
    public Project load(String projectDir, String projectId) {
        try {

            if (StringUtils.isEmpty(projectId)) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                String.format("Invalid project %s", projectId)
                                , AnsiColor.DEFAULT)
                );
                return null;
            }

            // read fx server details

            // create project

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            // ObjectMapper jasonMapper = new ObjectMapper();

            System.out.println("loading Fxfile.yaml...");
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Fxfile.yaml", "Loading");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File fxfile = FileUtils.getFile(new File(projectDir), "Fxfile.yaml");

            if (fxfile == null) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                String.format("Invalid project dir %s. Fxfile.yaml not found.", projectDir)
                                , AnsiColor.DEFAULT)
                );
                return null;
            }

            //new File(projectDir + "Fxfile.yml")
            Config config = yamlMapper.readValue(fxfile, Config.class);

            //System.out.println(config);

            Date lastSync = null;
            List<ProjectFile> projectFiles = null;
            Project project = null;


            project = getProjectById(projectId);


            if (project == null) {

                CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, config.getName(), String.format("No project with name [%s] found on FxServer", config.getName()));
                CredUtils.errors.set(Boolean.TRUE);
                return null;

                /*
                System.out.println(String.format("Fxfile.yaml project [%s] not found...creating new", config.getName()));
                CredUtils.taskLogger.get().append(String.format("Fxfile.yaml project [%s] not found...creating new", config.getName())).append("\n");
                Response<Project> projectResponse = createProject(config);

                if (projectResponse.isErrors()) {
                    System.err.println(projectResponse.getMessages());
                    return null;
                }

                project = projectResponse.getData();

                saveEnvs(config, project.getId());

                saveJobs(config, project.getId());

                System.out.println(String.format("Project created with id: [%s]", project.getId()));
                CredUtils.taskLogger.get().append(String.format("Project created with id: [%s]", project.getId())).append("\n");*/
            }


            System.out.println(String.format("Fxfile.yaml project [%s] found...", config.getName()));
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Fxfile.yaml", String.format("Fxfile.yaml project [%s] found...", config.getName()));

            projectFiles = getProjectChecksums(project.getId());

            String checksum = null;
            try {
                String fxfileContent = FileUtils.readFileToString(fxfile, "UTF-8");
                checksum = DigestUtils.md5Hex(fxfileContent);
            } catch (IOException e) {
                logger.warn(e.getLocalizedMessage());
                System.out.println(String.format("Failed loading [%s] file content with error [%s]", fxfile.getName(), e.getLocalizedMessage()));
                CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, fxfile.getName(), String.format("Failed loading [%s] file content with error [%s]", fxfile.getName(), e.getLocalizedMessage()));
                CredUtils.errors.set(Boolean.TRUE);
            }

            //System.out.println(projectFiles);
            //System.out.println("Fxfile.yaml checksum: " + checksum);

            if (!isChecksumPresent(projectFiles, fxfile, checksum)) {

                Response<Project> projectResponse = updateProject(project, config, fxfile, checksum);

                if (projectResponse.isErrors()) {
                    System.err.println(projectResponse.getMessages());
                }
                if (projectResponse.getData() != null) {
                    System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("%s [Synced]",
                                    org.apache.commons.lang3.StringUtils.rightPad(fxfile.getName(), 113)),
                            AnsiColor.DEFAULT));
                    //System.out.println(String.format("         : %s [Synced]", projectResponse.getData().getId()));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, fxfile.getName(), String.format("Project name [%s] id [%s] updated", projectResponse.getData().getName(), projectResponse.getData().getId()));
                }
            }

            // create dataset

            loadSuites(projectDir, yamlMapper, project.getId(), projectFiles);

            try {
                loadDataRecord(projectDir, project.getId(), projectFiles);
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
                System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
                CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "", String.format("Failed with error [%s]", e.getLocalizedMessage()));
            }
            return project;

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
            CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "", String.format("Failed with error [%s]", e.getLocalizedMessage()));
        }
        return null;
    }

    @PostConstruct
    private void setCreds() {
        if (StringUtils.isEmpty(CredUtils.username.get())) {
            CredUtils.username.set(this.username);
        }
        if (StringUtils.isEmpty(CredUtils.password.get())) {
            CredUtils.password.set(this.password);
        }
        if (StringUtils.isEmpty(CredUtils.url.get())) {
            CredUtils.url.set(this.url);
        }

    }

    private Project getProjectByName(Config config) {
        Project project = projectRestRepository.findByName(config.getName());
        return project;
    }

    private Project getProjectById(String id) {
        Response<Project> project = projectRestRepository.findById(id);
        if (project.isErrors() && !CollectionUtils.isEmpty(project.getMessages())) {
            logger.warn(project.getMessages().get(0).getValue());
        }
        return project.getData();
    }

    private Response<Project> createProject(Config config) {
        Project project = new Project();
        project.setName(config.getName());
        project.setLicenses(config.getLicenses());
        project.setDescription(config.getDescription());
        Response<Project> projectResponse = projectRestRepository.save(project);

        logger.info("project created with id [{}]...", project.getId());
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, config.getName(), String.format("project created with id [%s]...", project.getId()));
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

        Response<Project> projectResponse = projectRestRepository.update(project);

        //System.out.println(project);
        if (!CollectionUtils.isEmpty(config.getEnvironments())) {
            Response<List<Environment>> envResponses = mergeAndSaveEnvs(config, project.getId());

            if (envResponses.isErrors()) {
                for (Message m : envResponses.getMessages()) {
                    logger.warn(m.getValue());
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, fxfile.getName(), m.getValue());
                }
            }
        }

        if (!CollectionUtils.isEmpty(config.getJobs())) {
            Response<List<Job>> jobResponses = mergeAndSaveJobs(config, project.getId());

            if (jobResponses.isErrors()) {
                for (Message m : jobResponses.getMessages()) {
                    logger.warn(m.getValue());
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, fxfile.getName(), m.getValue());
                }
            }
        }

        Response<Boolean> importResponses = mergeAndSaveImports(config, project.getId());

        if (importResponses.isErrors()) {
            for (Message m : importResponses.getMessages()) {
                logger.warn(m.getValue());
                CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, fxfile.getName(), m.getValue());
            }
        }


        logger.info("project updated with id [{}]...", project.getId());
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, fxfile.getName(), "Project updated...");
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
                    old.setInactive(environment.isInactive());
                    old.setName(environment.getName());
                    old.setDescription(environment.getDescription());
                    old.setBaseUrl(environment.getBaseUrl());

                    List<Auth> list = new ArrayList<>();
                    for (Auth credential : environment.getAuths()) {
                        boolean authFound = false;
                        for (Auth a : old.getAuths()) {
                            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(credential.getName(), a.getName())) {
                                // update
                                copyAuth(credential, a);
                                authFound = true;
                                break;
                            }
                        }
                        if (!authFound) {
                            // new auth
                            Auth cred = new Auth();
                            cred.setName(credential.getName());
                            copyAuth(credential, cred);
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
        //System.out.println("Env updated...");
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "environments", "updated...");
        return response;
    }

    private void copyAuth(Auth credential, Auth cred) {

        cred.setAuthType(credential.getAuthType());

        cred.setUsername(credential.getUsername());
        cred.setPassword(credential.getPassword());

        // OAuth 2.0

        cred.setClientId(credential.getClientId());
        cred.setClientSecret(credential.getClientSecret());

        cred.setId(credential.getId());
        cred.setAccessTokenUri(credential.getAccessTokenUri());
        cred.setAuthorizationScheme(credential.getAuthorizationScheme());
        cred.setClientAuthenticationScheme(credential.getClientAuthenticationScheme());

        cred.setTokenName(credential.getTokenName());
        cred.setScope(credential.getScope());
        cred.setGrantType(credential.getGrantType());
        cred.setPreEstablishedRedirectUri(credential.getPreEstablishedRedirectUri());
        cred.setUseCurrentUri(credential.getUseCurrentUri());
        cred.setUserAuthorizationUri(credential.getUserAuthorizationUri());

        cred.setHeader_1(credential.getHeader_1());
        cred.setHeader_2(credential.getHeader_2());
        cred.setHeader_3(credential.getHeader_3());

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
                    old.setCron(job.getCron());
                    old.setIssueTracker(job.getIssueTracker());
                    old.setNotifications(job.getNotifications());
                    old.setInactive(job.isInactive());
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
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "jobs", "updated...");
        return listResponse;
    }

    private Response<Boolean> mergeAndSaveImports(Config config, String projectId) {
        ProjectImports projectImports = new ProjectImports();
        projectImports.setProjectId(projectId);
        projectImports.setImports(config.getImports());
        return projectRestRepository.saveImports(projectImports, projectId);
    }

    private List<Environment> extractEnvironments(Config config, String projectId) {
        // create env
        logger.info("loading env details...");

        List<Environment> environments = new ArrayList<>();
        for (com.fxlabs.fxt.sdk.beans.Environment environment : config.getEnvironments()) {
            Environment env = new Environment();
            env.setName(environment.getName());
            env.setInactive(environment.isInactive());
            env.setBaseUrl(environment.getBaseUrl());
            env.setDescription(environment.getDescription());
            env.setProjectId(projectId);

            //env.setProject(proj);

            List<Auth> list = new ArrayList<>();
            if (!CollectionUtils.isEmpty(environment.getAuths())) {
                for (com.fxlabs.fxt.sdk.beans.Auth credential : environment.getAuths()) {
                    Auth cred = new Auth();
                    cred.setName(credential.getName());

                    if (credential.getAuthType() != null) {
                        cred.setAuthType(AuthType.valueOf(credential.getAuthType().name()));
                    }

                    cred.setUsername(credential.getUsername());
                    cred.setPassword(credential.getPassword());
                    // OAuth 2.0
                    cred.setClientId(credential.getClientId());
                    cred.setClientSecret(credential.getClientSecret());

                    cred.setId(credential.getId());
                    cred.setAccessTokenUri(credential.getAccessTokenUri());
                    if (credential.getAuthorizationScheme() != null) {
                        cred.setAuthorizationScheme(AuthenticationScheme.valueOf(credential.getAuthorizationScheme().name()));
                    }
                    if (credential.getClientAuthenticationScheme() != null) {
                        cred.setClientAuthenticationScheme(AuthenticationScheme.valueOf(credential.getClientAuthenticationScheme().name()));
                    }
                    cred.setTokenName(credential.getTokenName());
                    cred.setScope(credential.getScope());
                    if (credential.getGrantType() != null) {
                        cred.setGrantType(GrantType.valueOf(credential.getGrantType().name()));
                    }
                    cred.setPreEstablishedRedirectUri(credential.getPreEstablishedRedirectUri());
                    cred.setUseCurrentUri(credential.getUseCurrentUri());
                    cred.setUserAuthorizationUri(credential.getUserAuthorizationUri());

                    cred.setHeader_1(credential.getHeader_1());
                    cred.setHeader_2(credential.getHeader_2());
                    cred.setHeader_3(credential.getHeader_3());

                    list.add(cred);
                }
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
            getNotification(jobProfile, job);

            job.setEnvironment(jobProfile.getEnvironment());

            job.setRegions(jobProfile.getRegions());
            job.setCron(jobProfile.getCron());
            getIssueTracker(jobProfile, job);
            job.setInactive(jobProfile.isInactive());
            ProjectMinimalDto proj = new ProjectMinimalDto();
            proj.setId(projectId);
            job.setProject(proj);

            jobs.add(job);

            logger.info("job created with id [{}]...", job.getId());
        }
        return jobs;
    }

    private void getIssueTracker(com.fxlabs.fxt.sdk.beans.Job jobProfile, Job job) {
        com.fxlabs.fxt.sdk.beans.JobIssueTracker projectIssueTracker = jobProfile.getIssueTracker();
        if (projectIssueTracker != null) {
            JobIssueTracker issueTracker1 = new JobIssueTracker();
            issueTracker1.setAccount(projectIssueTracker.getAccount());
            issueTracker1.setName(projectIssueTracker.getName());
            issueTracker1.setUrl(projectIssueTracker.getUrl());
            job.setIssueTracker(issueTracker1);
        }

    }

    private void getNotification(com.fxlabs.fxt.sdk.beans.Job jobProfile, Job job) {
        List<com.fxlabs.fxt.sdk.beans.JobNotification> projectNotifications = jobProfile.getNotifications();
        if (!CollectionUtils.isEmpty(projectNotifications)) {
            List<com.fxlabs.fxt.dto.project.JobNotification> listNotification= new ArrayList<>();
            for (com.fxlabs.fxt.sdk.beans.JobNotification jn : projectNotifications){
                com.fxlabs.fxt.dto.project.JobNotification jn_ = new JobNotification();
                jn_.setAccount(jn.getAccount());
                jn_.setChannel(jn.getChannel());
                jn_.setName(jn.getName());
                jn_.setTo(jn.getTo());
                listNotification.add(jn_);
            }
            job.setNotifications(listNotification);
        }

    }

    private void loadSuites(String projectDir, ObjectMapper yamlMapper, String projectId, List<ProjectFile> projectFiles) {
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Loading Test-Suites:",
                AnsiColor.DEFAULT));

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Note: Any file with not '.yaml' extension will be ignored.",
                AnsiColor.DEFAULT));
        CredUtils.taskLogger.get().append(BotLogger.LogType.WARN, "!.yaml", "Note: Any file with not '.yaml' extension will be ignored.");

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Note: All files need to have unique name irrespective of the folder they are in.",
                AnsiColor.DEFAULT));
        CredUtils.taskLogger.get().append(BotLogger.LogType.WARN, "Duplicate", "Note: All files need to have unique file name irrespective of the folder they are in.");


        File dataFolder = new File(projectDir + "test-suites");
        Collection<File> files = FileUtils.listFiles(dataFolder, new String[]{"yaml"}, true);

        // TODO - If duplicate file names reject processing with error.
        // TODO - Log all non .yaml files name as ignored.

        AtomicInteger totalFiles = new AtomicInteger(0);
        if (CollectionUtils.isEmpty(files)) {
            System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                    String.format("\nNo suites found in : [%s]", dataFolder.getAbsolutePath()),
                    AnsiColor.DEFAULT));
            logger.info("No suites found in : [%s]", dataFolder.getAbsolutePath());
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "", String.format("No suites found in : [%s]", dataFolder.getAbsolutePath()));
            return;
        }

        files.stream().forEach(file -> {

            try {
                TestSuite testSuite = null;
                String testSuiteContent = null;
                final String checksum;
                try {
                    testSuiteContent = FileUtils.readFileToString(file, "UTF-8");
                } catch (IOException e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.errors.set(Boolean.TRUE);
                }

                try {
                    //System.out.println(String.format("File [%s] last-modified [%s] last-sync [%s]", file.getName(), new Date(file.lastModified()), lastSync));
                    checksum = DigestUtils.md5Hex(testSuiteContent);

                    if (isChecksumPresent(projectFiles, file, checksum)) return;

                    testSuite = yamlMapper.readValue(file, TestSuite.class);
                } catch (Exception e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(AnsiOutput.toString(AnsiColor.RED,
                            String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage()),
                            AnsiColor.DEFAULT));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.errors.set(Boolean.TRUE);
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

                ProjectMinimalDto proj = new ProjectMinimalDto();
                proj.setId(projectId);
                testSuite.setProject(proj);
                try {
                    testSuiteRestRepository.save(testSuite);
                } catch (Exception e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.errors.set(Boolean.TRUE);
                }

                System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("Test-Suite: %s [Synced]",
                                org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100)),
                        AnsiColor.DEFAULT));

                CredUtils.taskLogger.get().append(BotLogger.LogType.INFO,
                        org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100),
                        "Synced");

                totalFiles.incrementAndGet();

            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
            }
        });

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("\nTotal Suites Loaded: [%s]", totalFiles),
                AnsiColor.DEFAULT));
        logger.info("test-suites successfully uploaded...");
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "", "test-suites successfully uploaded...");
    }

    private void loadDataRecord(String projectDir, String projectId, List<ProjectFile> projectFiles) {
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Loading Data-Records:",
                AnsiColor.DEFAULT));

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Note: Any file with not '.json' extension will be ignored.",
                AnsiColor.DEFAULT));
        CredUtils.taskLogger.get().append(BotLogger.LogType.WARN, "!.json", "Note: Any file with not '.json' extension will be ignored.");

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Note: All files need to have unique name irrespective of the folder they are in.",
                AnsiColor.DEFAULT));
        CredUtils.taskLogger.get().append(BotLogger.LogType.WARN, "Duplicate", "Note: All files need to have unique file name irrespective of the folder they are in.");


        File dataFolder = new File(projectDir + "test-suites");
        Collection<File> files = FileUtils.listFiles(dataFolder, new String[]{"json"}, true);

        // TODO - If duplicate file names reject processing with error.
        // TODO - Log all non .json files name as ignored.
        Response<Project> project = projectRestRepository.findById(projectId);
        NameDto nameDto = null;
        if (project.getData() != null) {
            nameDto = project.getData().getOrg();
        }
        NameDto finalNameDto = nameDto;
        AtomicInteger totalFiles = new AtomicInteger(0);

        if (CollectionUtils.isEmpty(files)) {
            System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                    String.format("\nNo data-records found in : [%s]", dataFolder.getAbsolutePath()),
                    AnsiColor.DEFAULT));
            logger.info("No data-records found in : [%s]", dataFolder.getAbsolutePath());
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "", String.format("No data-records found in : [%s]", dataFolder.getAbsolutePath()));
            return;
        }
        files.stream().forEach(file -> {

            try {
                String testSuiteContent = null;
                final String checksum;

                ObjectMapper jsonMapper = new ObjectMapper();
                try {
                    testSuiteContent = FileUtils.readFileToString(file, "UTF-8");
                } catch (IOException e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Failed loading [%s] file content with error [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.errors.set(Boolean.TRUE);
                }

                try {
                    //System.out.println(String.format("File [%s] last-modified [%s] last-sync [%s]", file.getName(), new Date(file.lastModified()), lastSync));
                    checksum = DigestUtils.md5Hex(testSuiteContent);

                    if (isChecksumPresent(projectFiles, file, checksum)) return;


                    DataSet dataSet = new DataSet();

                    if (StringUtils.isEmpty(dataSet.getName())) {
                        dataSet.setName(FilenameUtils.getBaseName(file.getName()));
                    }

                    ProjectMinimalDto proj = new ProjectMinimalDto();
                    proj.setId(projectId);
                    dataSet.setProject(proj);


                    Response<DataSet> dataSetResponse = null;
                    // set file content
                    Long lastModified = file.lastModified();
                    dataSet.setProps(new HashMap<>());
                    dataSet.getProps().put(Project.FILE_CONTENT, testSuiteContent);
                    dataSet.getProps().put(Project.MODIFIED_DATE, String.valueOf(lastModified));
                    dataSet.getProps().put(Project.MD5_HEX, checksum);
                    dataSet.getProps().put(Project.FILE_NAME, file.getName());

                    try {
                        dataSetResponse = dataSetRestRepository.save(dataSet);
                        dataRecordRestRepository.deleteAllByDataset(dataSetResponse.getData().getId());
                    } catch (Exception e) {
                        logger.warn(e.getLocalizedMessage());
                        System.out.println(String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
                        CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Failed loading [%s] with error [%s]", file.getName(), e.getLocalizedMessage()));
                        CredUtils.errors.set(Boolean.TRUE);
                    }
                    DataSet dataSet1 = dataSetResponse.getData();

                    //dataSet = jsonMapper.readValue(file, DataSet.class);
                    Iterator<String> itr =  null;
                    try {
                        String[] strArr = {};
                        strArr = jsonMapper.readValue(file, String[].class);
                        List<String> listArr = Arrays.asList(strArr);
                        itr = listArr.iterator();
                    }
                    catch (Exception ex){
                        logger.warn(ex.getLocalizedMessage());
//                        JsonNode rootNode = jsonMapper.readTree(file);
//                        if (rootNode.isArray()) {
//                            itr = rootNode.iterator();
//                        }
                    }
                    if (itr != null) {
                        List<String> skip = new ArrayList<>();
                        itr.forEachRemaining(item -> {
                            skip.add(item.toString());
                        });
                        List<List<String>> batch = Lists.partition(skip, 10);
                        for (List<String> list : batch) {
                            // Add your code here
                            List<DataRecord> dataRecords1 = new ArrayList<>();
                            for (String item : list) {
                                DataRecord record = new DataRecord();
                                record.setRecord(item);
                                record.setDataSet(dataSet1.getId());
//                            record.setOrg(finalNameDto);
//                            record.setProject(proj);
                                dataRecords1.add(record);
                            }
                            dataRecordRestRepository.saveAll(dataRecords1);
                        }
//
                    }
                } catch (Exception e) {
                    logger.warn(e.getLocalizedMessage());
                    System.out.println(AnsiOutput.toString(AnsiColor.RED,
                            String.format("DataSet: %s [%s]", file.getName(), e.getLocalizedMessage()),
                            AnsiColor.DEFAULT));
                    CredUtils.taskLogger.get().append(BotLogger.LogType.ERROR, file.getName(), String.format("Test-Suite: %s [%s]", file.getName(), e.getLocalizedMessage()));
                    CredUtils.errors.set(Boolean.TRUE);
                    return;
                }
                //logger.info("ds size: [{}]", values.length);


                System.out.println(AnsiOutput.toString(AnsiColor.GREEN,
                        String.format("Data-Record: %s [Synced]",
                                org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100)),
                        AnsiColor.DEFAULT));
                CredUtils.taskLogger.get().append(BotLogger.LogType.INFO,
                        org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100),
                        "Synced");

                totalFiles.incrementAndGet();
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
            }
        });

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("\nTotal Data-Records Loaded: [%s]", totalFiles),
                AnsiColor.DEFAULT));
        logger.info("Data-Records successfully uploaded...");
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "", "Data-Records successfully uploaded...");
    }

    private boolean isChecksumPresent(List<ProjectFile> projectFiles, File file, String checksum) {

        if (projectFiles != null && !CollectionUtils.isEmpty(projectFiles)) {

            Optional<ProjectFile> projectFileOptional = projectFiles.stream().filter(pf -> org.apache.commons.lang3.StringUtils.equals(checksum, pf.getChecksum()))
                    .findFirst();

            if (projectFileOptional.isPresent()) {
                System.out.println(AnsiOutput.toString(AnsiColor.WHITE,
                        String.format("Test-Suite: %s [Up-to-date]",
                                org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100)),
                        AnsiColor.DEFAULT));
                CredUtils.taskLogger.get().append(BotLogger.LogType.INFO,
                        org.apache.commons.lang3.StringUtils.rightPad(file.getName(), 100),
                        "Up-to-date");
                return true;
            }
        }
        return false;
    }

    private List<ProjectFile> getProjectChecksums(String projectId) {
        System.out.println("in getProjectChecksums");
        final Response<List<ProjectFile>> projectFilesResponse = this.projectRestRepository.findProjectChecksumsAll(projectId);
        System.out.println(" Files retrieved.... " + projectFilesResponse.getData().size());
        return projectFilesResponse.getData();
//        return new ArrayList<ProjectFile>();
    }

    private void lsJobs() {
    }

    private void lsProjects() {
        Response<List<Project>> list = projectRestRepository.findAll();

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

    private Long runJob(String jobId, String region, String tags, String envName, String suites) {
        Run run = runRestRepository.run(jobId, region, tags, envName, suites);
        System.out.println("");
        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("Running Job: %s Run: %s", jobId, run.getId()),
                AnsiColor.DEFAULT));

        int page = 0;
        int pageSize = 10;
        int count = 0;
        boolean isComplete = false;

        printSuiteHeader();

        while (!isComplete) {
            //System.out.println("inside");
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
                    //printDataSet(ds);
                }
            }

            Response<List<Suite>> responses = runRestRepository.findTestSuiteSummaryByRunId(run.getId(), page, pageSize);
            if (response != null && !CollectionUtils.isEmpty(response.getData())) {
                for (Suite suite : responses.getData()) {
                    if (suiteSet.add(suite)) {
                        printSuite(suite);
                    }
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
            } /*else if (run.getTask().getStatus() != TaskStatus.WAITING) {
                isComplete = true;
            }*/

            //if (isComplete) break;

            run = runRestRepository.findInstance(run.getId());
            if (run.getTask().getStatus() == TaskStatus.COMPLETED || run.getTask().getStatus() == TaskStatus.FAIL || run.getTask().getStatus() == TaskStatus.TIMEOUT) {
                isComplete = true;
            }
        }

        printSummary();

        run = runRestRepository.findInstance(run.getId());
        printRun(run, "\n");

        Long runId = run != null ? run.getRunId() : 0;

        return runId;
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
        /*Table table = new TableBuilder(new BeanListTableModel<Project>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);*/
    }

    private void printRun(Run run, String carriageReturn) {
        System.out.println("");

        Long per = 0L;
        if (run.getTask().getTotalTests() > 0)
            per = ((Long) (((run.getTask().getTotalTests() - run.getTask().getFailedTests()) * 100) / run.getTask().getTotalTests()));


        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "Run summary:",
                AnsiColor.DEFAULT));
        System.out.println(
                AnsiOutput.toString(AnsiColor.GREEN,
                        String.format(" Run Id: %s " +
                                        "\n URL: %s" +
                                        "\n Status: %s " +
                                        "\n Total Tests: %s " +
                                        "\n Total Pass: %s " +
                                        "\n Total Fail: %s " +
                                        "\n Success: %s " +
                                        "\n Data: %s Bytes" +
                                        "\n Total Time: %s ms",
                                run.getRunId(),
                                CredUtils.url.get() + "/#/app/jobs/" + run.getJob().getId() + "/runs/" + run.getId(),
                                run.getTask().getStatus(),
                                run.getTask().getTotalTests(),
                                run.getTask().getTotalTestCompleted(),
                                run.getTask().getFailedTests(),
                                per + "%",
                                run.getTask().getTotalTime(),
                                run.getTask().getTotalBytes(),
                                carriageReturn)
                        , AnsiColor.DEFAULT)
        );
        //System.out.println("");
    }

    private void printFailedSuites(Set<TestSuiteResponse> dataSets, File f) {

        /*System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                "\nExecution logs:",
                AnsiColor.DEFAULT));*/

        dataSets.forEach(suite -> {
            if (!org.apache.commons.lang3.StringUtils.equalsIgnoreCase(suite.getStatus(), "pass") &&
                    !StringUtils.isEmpty(suite.getLogs())) {
                //System.out.println(suite.getTestSuite());
                printErrorLogs(f, suite.getLogs());
            }
        });
    }

    private void printErrorLogs(File file, String logs) {
        try {
            FileUtils.writeStringToFile(file, logs, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT, logs, AnsiColor.DEFAULT)
        );*/
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

    private void printSuiteHeader() {
        System.out.println("");
        System.out.println(
                AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                        String.format("%s %s %s %s %s %s",
                                org.apache.commons.lang3.StringUtils.rightPad("Suite", 100),
                                org.apache.commons.lang3.StringUtils.rightPad("Pass", 10),
                                org.apache.commons.lang3.StringUtils.rightPad("Fail", 10),
                                org.apache.commons.lang3.StringUtils.rightPad("Success", 20),
                                org.apache.commons.lang3.StringUtils.rightPad("Time (ms)", 20),
                                org.apache.commons.lang3.StringUtils.rightPad("Data (B)", 20),
                                org.apache.commons.lang3.StringUtils.rightPad("Result", 15)),
                        AnsiColor.DEFAULT)
        );
    }

    private void printSummary() {
        int tests = 0;
        int fails = 0;
        long time = 0;
        long size = 0L;
        long per = 0L;
        for (Suite s : suiteSet) {
            tests += s.getTests() != null ? s.getTests() : 0;
            fails += s.getFailed() != null ? s.getFailed() : 0;
            time += s.getTime() != null ? s.getTime() : 0;
            size += s.getSize() != null ? s.getSize() : 0;
        }
        if (tests > 0) {
            per = ((tests - fails) * 100) / tests;
        }
        System.out.println(org.apache.commons.lang3.StringUtils.rightPad("-", 195, "-"));
        System.out.println(
                AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                        String.format("%s %s %s %s %s %s",
                                org.apache.commons.lang3.StringUtils.rightPad("", 100),
                                org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(tests - fails), 10),
                                org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(fails), 10),
                                org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(per) + "%", 20),
                                org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(time), 20),
                                org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(size), 20),
                                org.apache.commons.lang3.StringUtils.rightPad("", 15)),
                        AnsiColor.DEFAULT)
        );
        System.out.println(org.apache.commons.lang3.StringUtils.rightPad("-", 195, "-"));

    }

    private void printSuites(Set<Suite> suites) {
        for (Suite suite : suites) {
            printSuite(suite);
        }
    }

    private void printSuite(Suite suite) {
        String result = suite.getFailed() == 0 ? "Pass" : "Fail";
        if (suite.getFailed() > 0) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("%s %s %s %s %s %s",
                                    org.apache.commons.lang3.StringUtils.rightPad(suite.getSuiteName(), 100),
                                    org.apache.commons.lang3.StringUtils.rightPad("" + (suite.getTests() - suite.getFailed()), 10),
                                    org.apache.commons.lang3.StringUtils.rightPad("" + suite.getFailed(), 10),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf((long) (((suite.getTests() - suite.getFailed()) * 100) / suite.getTests())) + "%", 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(suite.getTime()), 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(suite.getSize()), 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(result, 15)),
                            AnsiColor.DEFAULT)
            );
        } else {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("%s %s %s %s %s %s",
                                    org.apache.commons.lang3.StringUtils.rightPad(suite.getSuiteName(), 100),
                                    org.apache.commons.lang3.StringUtils.rightPad("" + (suite.getTests() - suite.getFailed()), 10),
                                    org.apache.commons.lang3.StringUtils.rightPad("" + suite.getFailed(), 10),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf((long) (((suite.getTests() - suite.getFailed()) * 100) / suite.getTests())) + "%", 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(suite.getTime()), 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(String.valueOf(suite.getSize()), 20),
                                    org.apache.commons.lang3.StringUtils.rightPad(result, 15)),
                            AnsiColor.DEFAULT)
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
        /*Table table = new TableBuilder(new BeanListTableModel<com.fxlabs.fxt.dto.project.Job>(list, header))
                .addOutlineBorder(BorderStyle.fancy_light)
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_light)
                .addHeaderAndVerticalsBorders(BorderStyle.fancy_light)
                .build();
        String result = table.render(300);
        System.out.println(result);*/
    }

}