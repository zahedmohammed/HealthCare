package com.fxlabs.fxt.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.*;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.sdk.beans.Config;
import com.fxlabs.fxt.sdk.rest.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:/fx-sdk.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:/opt/fx/fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:/var/fx/fx.properties")
@PropertySource(ignoreResourceNotFound = true, value = "file:${user.home}/fxt/fx.properties")
public class FxCommandService {

    final Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${url:#{null}}")
    protected String url;
    @Value("${username:#{null}}")
    protected String username;
    @Value("${password:#{null}}")
    protected String password;

    // Fx server connection details
    protected Set<TestSuiteResponse> dataSets = new HashSet<>();
    protected Set<Suite> suiteSet = new HashSet<>();

    @Autowired
    private UsersRestRepository usersRestRepository;
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

    public Response<Users> login() {
        return this.usersRestRepository.findByLogin();
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

        System.out.println(String.format("locating project %s...", projectName));
        Response<Project> response = projectRepository.findByOrgAndName(projectName);
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

        Date loadEnd = new Date();

        //System.out.println("running job...");
        dataSets = new HashSet<>();
        suiteSet = new HashSet<>();
        runJob(jobId, region, tags, envName, suites);

        System.out.println(
                AnsiOutput.toString(AnsiColor.DEFAULT,
                        String.format("Total Time: %s ms",
                                (new Date().getTime() - loadEnd.getTime()))
                        , AnsiColor.DEFAULT)
        );

        printFailedSuites(dataSets);

        //printSuiteHeader();

        //printSuites(suiteSet);



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

            System.out.println("loading Fxfile.yaml...");
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Fxfile.yaml", "Loading");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File fxfile = FileUtils.getFile(new File(projectDir), "Fxfile.yaml");

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


            System.out.println(String.format("Fxfile.yaml project [%s] exists and last-synced date [%s]", config.getName(), project.getLastSync()));
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Fxfile.yaml", String.format("Fxfile.yaml project [%s] exists and last-synced date [%s]", config.getName(), project.getLastSync()));

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
            //System.out.println(checksum);

            if (!isChecksumPresent(projectFiles, fxfile, checksum)) {

                Response<Project> projectResponse = updateProject(project, config, fxfile, checksum);

                if (projectResponse.isErrors()) {
                    System.err.println(projectResponse.getMessages());
                }
                System.out.println(String.format("Project id: [%s] updated", projectResponse.getData().getId()));
                CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, fxfile.getName(), String.format("Project id: [%s] updated", projectResponse.getData().getId()));
            }

            // create dataset

            loadSuites(projectDir, yamlMapper, project.getId(), projectFiles);

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
        Project project = projectRepository.findByName(config.getName());
        return project;
    }

    private Project getProjectById(String id) {
        Response<Project> project = projectRepository.findById(id);
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
        Response<Project> projectResponse = projectRepository.save(project);

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

        Response<Project> projectResponse = projectRepository.update(project);

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
        System.out.println("Env updated...");
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
        return projectRepository.saveImports(projectImports, projectId);
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
            job.setCron(jobProfile.getCron());
            job.setIssueTracker(jobProfile.getIssueTracker());
            job.setInactive(jobProfile.isInactive());
            ProjectMinimalDto proj = new ProjectMinimalDto();
            proj.setId(projectId);
            job.setProject(proj);

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
        files.parallelStream().forEach(file -> {

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
                    String.format("Test-Suite: %s [Synced]", file.getName()),
                    AnsiColor.DEFAULT));
            CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Synced");

            totalFiles.incrementAndGet();
        });

        System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                String.format("\nTotal Suites Loaded: [%s]", totalFiles),
                AnsiColor.DEFAULT));
        logger.info("test-suites successfully uploaded...");
        CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, "", "test-suites successfully uploaded...");
    }

    private boolean isChecksumPresent(List<ProjectFile> projectFiles, File file, String checksum) {
        if (projectFiles != null && !CollectionUtils.isEmpty(projectFiles)) {
            Optional<ProjectFile> projectFileOptional = projectFiles.stream().filter(pf -> org.apache.commons.lang3.StringUtils.equals(checksum, pf.getChecksum()))
                    .findFirst();

            if (projectFileOptional.isPresent()) {
                System.out.println(AnsiOutput.toString(AnsiColor.WHITE,
                        String.format("Test-Suite: %s [Up-to-date]", file.getName()),
                        AnsiColor.DEFAULT));
                CredUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Up-to-date");
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
            } else if (run.getTask().getStatus() != TaskStatus.WAITING) {
                isComplete = true;
            }

            //if (isComplete) break;

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

    private void printSuiteHeader() {
        System.out.println("");
        System.out.println(
                AnsiOutput.toString(AnsiColor.BRIGHT_WHITE,
                        String.format("%s %s %s %s %s %s",
                                org.apache.commons.lang3.StringUtils.leftPad("Result", 15),
                                org.apache.commons.lang3.StringUtils.leftPad("Suite", 50),
                                org.apache.commons.lang3.StringUtils.leftPad("Total/Passed", 20),
                                org.apache.commons.lang3.StringUtils.leftPad("Success (%)", 20),
                                org.apache.commons.lang3.StringUtils.leftPad("Time (ms)", 20),
                                org.apache.commons.lang3.StringUtils.leftPad("Data (B)", 20)),
                                AnsiColor.DEFAULT)
                );
    }

    private void printSuites(Set<Suite> suites) {
        for (Suite suite : suites) {
            printSuite(suite);
        }
    }
    private void printSuite(Suite suite) {
        String result = suite.getFailed() == 0 ? "Passed" : "Failed";
        if (suite.getFailed() > 0) {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.RED,
                            String.format("%s %s %s %s %s %s",
                                    org.apache.commons.lang3.StringUtils.leftPad(result, 15),
                                    org.apache.commons.lang3.StringUtils.leftPad(suite.getSuiteName(), 50),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getTests()) + "/" + String.valueOf(suite.getTests() - suite.getFailed()), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf((long) (((suite.getTests() - suite.getFailed()) * 100) / suite.getTests())), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getTime()), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getSize()), 20)),
                            AnsiColor.DEFAULT)
            );
        } else {
            System.out.println(
                    AnsiOutput.toString(AnsiColor.GREEN,
                            String.format("%s %s %s %s %s %s",
                                    org.apache.commons.lang3.StringUtils.leftPad(result, 15),
                                    org.apache.commons.lang3.StringUtils.leftPad(suite.getSuiteName(), 50),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getTests()) + "/" + String.valueOf(suite.getTests() - suite.getFailed()), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf((long) (((suite.getTests() - suite.getFailed()) * 100) / suite.getTests())), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getTime()), 20),
                                    org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(suite.getSize()), 20)),
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