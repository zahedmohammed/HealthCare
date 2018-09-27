package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.dto.run.*;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.processors.send.InstantRunTaskRequestProcessor;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.project.TestSuiteService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class RunServiceImpl extends GenericServiceImpl<Run, com.fxlabs.fxt.dto.run.Run, String> implements RunService {

    private JobService jobService;
    //private RunTaskRequestProcessor taskProcessor;
    private RunRepository repository;
    private TestSuiteRepository testSuiteRepository;
    private TestSuiteResponseRepository testSuiteResponseRepository;
    private TestSuiteResponseConverter testSuiteResponseConverter;
    private TestSuiteESRepository testSuiteESRepository;
    private ProjectService projectService;
    private TestCaseResponseITRepository testCaseResponseITRepository;
    private SuiteESRepository suiteESRepository;
    private SuiteConverter suiteConverter;
    private TestSuiteService testSuiteService;
    private TestSuiteConverter testSuiteConverter;
    private InstantRunTaskRequestProcessor instantRunTaskRequestProcessor;
    private EnvironmentRepository environmentRepository;
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    public RunServiceImpl(RunRepository repository, RunConverter converter, JobService projectJobService, EnvironmentRepository environmentRepository,
            /*RunTaskRequestProcessor taskProcessor, */TestSuiteRepository projectDataSetRepository, TestSuiteConverter testSuiteConverter,
                          TestSuiteResponseRepository dataSetRepository, TestSuiteResponseConverter dataSetConverter, TestCaseResponseITRepository testCaseResponseITRepository,
                          TestSuiteESRepository testSuiteESRepository, ProjectService projectService, TestSuiteService testSuiteService,
                          SuiteESRepository suiteESRepository, SuiteConverter suiteConverter, InstantRunTaskRequestProcessor instantRunTaskRequestProcessor,
                          TestSuiteResponseESRepository testSuiteResponseESRepository
    ) {
        super(repository, converter);
        this.repository = repository;
        this.jobService = projectJobService;
        //this.taskProcessor = taskProcessor;
        this.testSuiteRepository = projectDataSetRepository;
        this.testSuiteResponseRepository = dataSetRepository;
        this.testSuiteResponseConverter = dataSetConverter;
        this.testSuiteESRepository = testSuiteESRepository;
        this.projectService = projectService;
        this.suiteESRepository = suiteESRepository;
        this.suiteConverter = suiteConverter;
        this.testSuiteConverter = testSuiteConverter;
        this.instantRunTaskRequestProcessor = instantRunTaskRequestProcessor;
        this.environmentRepository = environmentRepository;
        this.testSuiteService = testSuiteService;
        this.testSuiteResponseESRepository = testSuiteResponseESRepository;
        this.testCaseResponseITRepository = testCaseResponseITRepository;

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobId(String jobId, String user, Pageable pageable) {
        jobService.isUserEntitled(jobId, user);
        Page<Run> page = ((RunRepository) repository).findByJobId(jobId, pageable);
        List<com.fxlabs.fxt.dto.run.Run> runs = converter.convertToDtos(page.getContent());
        for (com.fxlabs.fxt.dto.run.Run run : runs) {
            String region = null;
            if (!CollectionUtils.isEmpty(run.getAttributes())) {
                region = run.getAttributes().get(RunConstants.REGION);
            }
            if (StringUtils.isNotEmpty(region)) {
                run.setRegions(region);
            } else {
                run.setRegions(run.getJob().getRegions());
            }
            if (run.getTask().getStartTime() != null && run.getTask().getEndTime() != null) {
                run.getTask().setTimeTaken(run.getTask().getEndTime().getTime() - run.getTask().getStartTime().getTime());
                run.getTask().setTimeSaved(run.getTask().getTotalTime() - run.getTask().getTimeTaken());
            }
        }
        return new Response<List<com.fxlabs.fxt.dto.run.Run>>(runs, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobIdForSaving(String jobId, String user, Pageable pageable) {
        jobService.isUserEntitled(jobId, user);
        Page<Run> page = ((RunRepository) repository).findByJobId(jobId, pageable);
        return new Response<List<com.fxlabs.fxt.dto.run.Run>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> run(String jobId, String region, String tags, String env, String suites, String categories, String user) {
        Response<com.fxlabs.fxt.dto.run.Run> response = null;
        try {

            jobService.isUserEntitled(jobId, user);

            Response<Job> jobResponse = this.jobService.findById(jobId, user);

            // Create Run
            com.fxlabs.fxt.dto.run.Run run = new com.fxlabs.fxt.dto.run.Run();
            run.setJob(jobResponse.getData());
            // read last run and
            Long maxId = ((RunRepository) repository).findMaxRunId(jobId);
            if (maxId == null) {
                maxId = 0L;
            }
            run.setRunId(maxId + 1L);

            Map<String, String> attributes = new HashMap<>();

            if (StringUtils.isNotEmpty(region)) {
                attributes.put(RunConstants.REGION, region);
            }
            if (StringUtils.isNotEmpty(env)) {
                attributes.put(RunConstants.ENV, env);
            }
            if (StringUtils.isNotEmpty(tags)) {
                attributes.put(RunConstants.TAGS, tags);
            }
            if (StringUtils.isNotEmpty(suites)) {
                attributes.put(RunConstants.SUITES, suites);
            }
            if (StringUtils.isNotEmpty(categories)) {
                attributes.put(RunConstants.CATEGORIES, categories);
            }

            run.setAttributes(attributes);

            // Create Task
            RunTask task = new RunTask();
            task.setName(new Date().toString());
            task.setStatus(TaskStatus.WAITING);
            task.setStartTime(new Date());
            task.setTotalTests(0L);

            // TODO - find total tests by Tags
            Long totalTests = 0l;

            if (StringUtils.isNotEmpty(tags)) {
                //totalTests = testSuiteESRepository.countByProjectIdAndTypeAndTagsIn(jobResponse.getData().getProject().getId(), TestSuiteType.SUITE.toString(), Arrays.asList(_tags));
                //task.setTotalTests(totalTests);
            } else {
                //totalTests = testSuiteESRepository.countByProjectIdAndType(jobResponse.getData().getProject().getId(), TestSuiteType.SUITE.toString());
                //task.setTotalTests(totalTests);
            }

            if (StringUtils.isNotEmpty(suites)) {
                int total = StringUtils.split(suites, ",").length;
                task.setTotalTests(new Long(total));
            }

            run.setTask(task);

            response = save(run);

            Job job = jobResponse.getData();

            Response<Job> jobResponse1 = jobService.save(job, user);

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response = new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, ex.getLocalizedMessage()));
        }

        //taskProcessor.process(response.getData());
        // Copy ProjectDataSets to DataSets.
        return response;
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.run.TestSuiteResponse>> runTestSuite(String region, String env, String user, TestSuite dto) {
        Response<List<com.fxlabs.fxt.dto.run.TestSuiteResponse>> response = new Response<>();
        try {


            Map<String, String> attributes = new HashMap<>();

            if (StringUtils.isNotEmpty(region)) {
                attributes.put(RunConstants.REGION, region);
            }
            if (StringUtils.isNotEmpty(env)) {
                attributes.put(RunConstants.ENV, env);
            }


            // Create Task
            RunTask task = new RunTask();
            task.setName(new Date().toString());
            task.setStatus(TaskStatus.WAITING);
            task.setStartTime(new Date());
            task.setTotalTests(0L);

            // TODO - find total tests by Tags
            Long totalTests = 0l;

            Optional<Environment> optionalEnvironment = environmentRepository.findByIdAndInactive(env, false);

            if (!optionalEnvironment.isPresent()) {
                throw new FxException("Invalid Evnvironment");
            }

            // Job job = jobResponse.getData();

            Response<TestSuite> testSuiteResponse = testSuiteService.update(dto, user, false);


            List<com.fxlabs.fxt.dto.run.TestSuiteResponse> responses = instantRunTaskRequestProcessor.process(region, optionalEnvironment.get(), testSuiteConverter.convertToEntity(testSuiteResponse.getData()));
            response.setData(responses);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response = new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, ex.getLocalizedMessage()));
        }

        //taskProcessor.process(response.getData());
        // Copy ProjectDataSets to DataSets.
        return response;
    }


    @Override
    public Response<List<TestSuiteResponse>> findByRunId(String runId, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseRepository.findByRunId(runId, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> findRunById(String runId, String user) {
        Run run = this.repository.findByRunId(runId);
        com.fxlabs.fxt.dto.run.Run run1 = this.converter.convertToDto(run);
        run1.setRegions(run.getAttributes().get("REGION"));
        return new Response<com.fxlabs.fxt.dto.run.Run>(run1);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> findRunByJobIdAndRunNo(String jobId, Long runNo, String nav, String user) {
        Optional<Run> run = null;
        if (nav.equalsIgnoreCase("prev")) {
            run = this.repository.findRunByJobIdAndRunIdCheckPrevRun(jobId, runNo);

        } else if (nav.equalsIgnoreCase("next")) {
            run = this.repository.findRunByJobIdAndRunIdCheckNextRun(jobId, runNo);

        } else {
            run = this.repository.findRunByJobIdAndRunId(jobId, runNo);
        }

        if (run.isPresent()) {
            com.fxlabs.fxt.dto.run.Run run1 = this.converter.convertToDto(run.get());
            run1.setRegions(run.get().getAttributes().get("REGION"));
            return new Response<com.fxlabs.fxt.dto.run.Run>(run1);
        } else {

            return new Response<com.fxlabs.fxt.dto.run.Run>()
                    .withErrors(true).withMessage(new Message(MessageType.ERROR, null, "No runs found "));
        }
    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> stopRun(String runId, String user) {

        if (StringUtils.isEmpty(runId)) {
            throw new FxException("Invalid run");
        }
        Run run = this.repository.findByRunId(runId);

        if (run == null) {
            throw new FxException("Invalid run");
        }

        run.getTask().setStatus(com.fxlabs.fxt.dao.entity.run.TaskStatus.STOPPED);

        run = this.repository.save(run);
        com.fxlabs.fxt.dto.run.Run run1 = this.converter.convertToDto(run);

        return new Response<com.fxlabs.fxt.dto.run.Run>(run1);
    }


    @Override
    public Response<List<TestSuiteResponse>> findByPk(String id, String name, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseESRepository.findByRunIdAndTestSuite(id, name, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<TestSuiteResponse>> findByTestSuite(String jobId, String testSuite, String user, Pageable pageable) {

        List<com.fxlabs.fxt.dao.entity.run.Run> allRuns = repository.findAllRunsByJobId(jobId);
        List<String> runIds = new ArrayList<>();
        for (com.fxlabs.fxt.dao.entity.run.Run run : allRuns) {
            runIds.add(run.getId());
        }

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseESRepository.findByTestSuiteAndRunIdIn(testSuite, runIds, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<List<Suite>> findSummaryByRunId(String runId, String org, Pageable pageable) {

        // TODO check org is entitled to runId
        Page<com.fxlabs.fxt.dao.entity.run.Suite> page = this.suiteESRepository.findByRunId(runId, pageable);

        List<Suite> dataSets = suiteConverter.convertToDtos(page.getContent());
        return new Response<List<Suite>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<Suite>> search(String runId, String category, String keyword, String status, String org, Pageable pageable) {
        // TODO check org is entitled to runId

        // 1. filter by category and search string
        // 2. filter by category
        // 3. filter by search
        // 4. filter by severity and search string
        // 5. filter by status(pass/fail) and search string
        Page<com.fxlabs.fxt.dao.entity.run.Suite> page = null;

        if (StringUtils.isNotEmpty(category) && StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(status)) {
            // All three params present
            if (StringUtils.equalsIgnoreCase("failed", status)) {
                page = this.suiteESRepository.findByRunIdAndCategoryAndSuiteNameContainingIgnoreCaseAndFailedGreaterThan(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), keyword, new Long(0), pageable);
            } else if (StringUtils.equalsIgnoreCase("passed", status)) {
                page = this.suiteESRepository.findByRunIdAndCategoryAndSuiteNameContainingIgnoreCaseAndFailed(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), keyword, new Long(0), pageable);
            }
        } else if (StringUtils.isNotEmpty(category) && StringUtils.isNotEmpty(keyword) && StringUtils.isEmpty(status)) {
            // category and keyword present
            page = this.suiteESRepository.findByRunIdAndCategoryAndSuiteNameContainingIgnoreCase(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), keyword, pageable);
        } else if (StringUtils.isNotEmpty(category) && StringUtils.isEmpty(keyword) && StringUtils.isNotEmpty(status)) {
            // category and status present
            if (StringUtils.equalsIgnoreCase("failed", status)) {
                page = this.suiteESRepository.findByRunIdAndCategoryAndFailedGreaterThan(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), new Long(0), pageable);
            } else if (StringUtils.equalsIgnoreCase("passed", status)) {
                page = this.suiteESRepository.findByRunIdAndCategoryAndFailed(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), new Long(0), pageable);
            }
        } else if (StringUtils.isEmpty(category) && StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(status)) {
            // keyword and status present
            if (StringUtils.equalsIgnoreCase("failed", status)) {
                page = this.suiteESRepository.findByRunIdAndSuiteNameContainingIgnoreCaseAndFailedGreaterThan(runId, keyword, new Long(0), pageable);
            } else if (StringUtils.equalsIgnoreCase("passed", status)) {
                page = this.suiteESRepository.findByRunIdAndSuiteNameContainingIgnoreCaseAndFailed(runId, keyword, new Long(0), pageable);
            }
        } else if (StringUtils.isNotEmpty(category) && StringUtils.isEmpty(keyword) && StringUtils.isEmpty(status)) {
            // Only category is present
            page = this.suiteESRepository.findByRunIdAndCategory(runId, com.fxlabs.fxt.dao.entity.project.TestSuiteCategory.valueOf(category), pageable);
        } else if (StringUtils.isEmpty(category) && StringUtils.isNotEmpty(keyword) && StringUtils.isEmpty(status)) {
            // Only keyword is present
            page = this.suiteESRepository.findByRunIdAndSuiteNameContainingIgnoreCase(runId, keyword, pageable);
        } else if (StringUtils.isEmpty(category) && StringUtils.isEmpty(keyword) && StringUtils.isNotEmpty(status)) {
            // TODO: Only status is present
            if (StringUtils.equalsIgnoreCase("failed", status)) {
                page = this.suiteESRepository.findByRunIdAndFailedGreaterThan(runId, new Long(0), pageable);
            } else if (StringUtils.equalsIgnoreCase("passed", status)) {
                page = this.suiteESRepository.findByRunIdAndFailed(runId, new Long(0), pageable);
            }
        }

        List<Suite> dataSets = suiteConverter.convertToDtos(page.getContent());
        return new Response<List<Suite>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }


    private Date getCurrentMonthStartDate() {
        Date date = new Date();

        date = DateUtils.setMilliseconds(date, 0);
        date = DateUtils.setSeconds(date, 0);
        date = DateUtils.setMinutes(date, 0);
        date = DateUtils.setHours(date, 0);
        date = DateUtils.setDays(date, 1);
        return date;

    }

    private Date getFromDate(String fromdate) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fromdate);
        // Date date = new Date();
        date = DateUtils.setMilliseconds(date, 0);
        date = DateUtils.setSeconds(date, 0);
        date = DateUtils.setMinutes(date, 0);
        date = DateUtils.setHours(date, 0);
        return date;
    }

    private Date getToDate(String todate) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(todate);
        // Date date = new Date();
        date = DateUtils.setMilliseconds(date, 0);
        date = DateUtils.setSeconds(date, 59);
        date = DateUtils.setMinutes(date, 59);
        date = DateUtils.setHours(date, 23);
        return date;
    }

    @Override
    public Response<Long> count(String user, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {


            Long count = repository.countByJobProjectIdAndCreatedDateGreaterThan(p.getId(), getCurrentMonthStartDate());

//            Long count = repository.countByJobProjectId(p.getId());
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countBugs(String orgId, String user, Pageable pageable) {

        Response<List<Project>> projectsResponse = projectService.findProjects(orgId, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);
        Long totalBugsCount;
        projectsResponse.getData().stream().forEach(p -> {

            // String itId = p.getName() + "//" + "%";
            Response<List<Job>> jobsResponse = jobService.findByProjectId(p.getId(), user, pageable);
            jobsResponse.getData().stream().forEach(j -> {
                String itId = p.getName() + "//" + j.getId() + "//" + "%";

                //  Long count = testCaseResponseITRepository.findSumByTestCaseResponseIssueTrackerIdLike(itId, getCurrentMonthStartDate());
                Long count = testCaseResponseITRepository.sumByProjectIdAndJobIdAndModifiedDate(p.getId(), j.getId(), getCurrentMonthStartDate());
                logger.info("Check project bug count {}", count);
                if (count != null) {
                    al.getAndAdd(count);
                }
            });
        });
        logger.info("Check bug count {}", al.get());

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countRunsByDates(String orgId, String fromDate, String toDate) {

        Response<List<Project>> projectsResponse = projectService.findProjectsByOrgId(orgId);
/*        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }*/

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {


            Long count = null;
            try {
                count = repository.countByJobProjectIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(p.getId(), getFromDate(fromDate), getToDate(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countTestsByDates(String orgId, String fromDate, String toDate) {
        Response<List<Project>> projectsResponse = projectService.findProjectsByOrgId(orgId);
     /*   if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }
*/
        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = null;
            try {
                count = repository.countTestsByProjectAndCreatedDateGreaterThanAndCreatedDateLessThanEqual(p.getId(), getFromDate(fromDate), getToDate(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public Response<Long> countTimeByDates(String orgId, String fromDate, String toDate) {

        Response<List<Project>> projectsResponse = projectService.findProjectsByOrgId(orgId);
  /*      if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }
*/
        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = null;
            try {
                count = repository.countTimeByProjectAndCreatedDateGreaterThanAndCreatedDateLessThanEqual(p.getId(), getFromDate(fromDate), getToDate(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countBytesByDates(String orgId, String fromDate, String toDate) {
        Response<List<Project>> projectsResponse = projectService.findProjectsByOrgId(orgId);
  /*      if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }
*/
        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = null;
            try {
                count = repository.countBytesByProjectAndCreatedDateGreaterThanAndCreatedDateLessThanEqual(p.getId(), getFromDate(fromDate), getToDate(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countBugsByDates(String orgId, String currentAuditor, String fromDate, String toDate) {

        Response<List<Project>> projectsResponse = projectService.findProjectsByOrgId(orgId);
       /* if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }
*/
        AtomicLong al = new AtomicLong(0);
        Long totalBugsCount;
        projectsResponse.getData().stream().forEach(p -> {

            // String itId = p.getName() + "//" + "%";
            Response<List<Job>> jobsResponse = jobService.findByProjectId(p.getId(), currentAuditor);
            jobsResponse.getData().stream().forEach(j -> {
                String itId = p.getName() + "//" + j.getId() + "//" + "%";

                //  Long count = testCaseResponseITRepository.findSumByTestCaseResponseIssueTrackerIdLike(itId, getCurrentMonthStartDate());
                Long count = null;
                try {
                    count = testCaseResponseITRepository.sumByProjectIdAndJobIdAndModifiedDateGreaterThanAndCreatedDateLessThanEqual(p.getId(), j.getId(), getFromDate(fromDate), getToDate(toDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                logger.info("Check project bug count {}", count);
                if (count != null) {
                    al.getAndAdd(count);
                }
            });
        });
        logger.info("Check bug count {}", al.get());

        return new Response<>(al.get());
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.run.Run>> findAll(String user, Pageable pageable) {
        return super.findAll(user, pageable);
    }

    @Override
    public Response<Long> countTests(String user, Pageable pageable) {
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {

            //  Long count = repository.countTestsByProject(p.getId());
            Long count = repository.countTestsByProjectAndCreatedDateGreaterThan(p.getId(), getCurrentMonthStartDate());

            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public Response<Long> countBytes(String user, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = repository.countBytesByProjectAndCreatedDateGreaterThan(p.getId(), getCurrentMonthStartDate());
            //    Long count = repository.countBytesByProject(p.getId());
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> findById(String s, String user) {
        return super.findById(s, user);
    }

    @Override
    public Response<Long> countTime(String user, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = repository.countTimeByProjectAndCreatedDateGreaterThan(p.getId(), getCurrentMonthStartDate());
            //  Long count = repository.countTimeByProject(p.getId());
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }


    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }


    @Override
    public Response<RunSavings> getRunSavings(String runId, String user) {

        RunSavings savings = new RunSavings();

        long timeSaved = 0L;

        Run run = this.repository.findByRunId(runId);
        if (run.getTask().getStartTime() != null && run.getTask().getEndTime() != null)
            timeSaved = run.getTask().getTotalTime() - (run.getTask().getEndTime().getTime() - run.getTask().getStartTime().getTime());

        savings.setTimeSaved(timeSaved);

        return new Response<RunSavings>(savings);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> deleteRun(String jobId, Long runId, String user) {

        Optional<Run> optionalRun = this.repository.findByJobIdAndRunId(jobId, runId);

        if (!optionalRun.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid Run Id..."));
        }

        Page<com.fxlabs.fxt.dao.entity.run.Suite> optionalSuite = this.suiteESRepository.findByRunId(runId.toString(), Pageable.unpaged());
        this.suiteESRepository.deleteAll(optionalSuite.getContent());

        return delete(optionalRun.get().getId(), user);
    }


    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> reRun(String jobId, Long runId, String user) {

        Optional<Run> optionalRun = this.repository.findByJobIdAndRunId(jobId, runId);

        if (!optionalRun.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid Run Id..."));
        }
        Run run = optionalRun.get();
        Map<String, String> attributes = run.getAttributes();
        String region = attributes.get(RunConstants.REGION);
        String env = attributes.get(RunConstants.ENV);
        String tags = attributes.get(RunConstants.TAGS);
        String suites = attributes.get(RunConstants.SUITES);
        String categories = attributes.get(RunConstants.CATEGORIES);
        return run(run.getJob().getId(), region, tags, env, suites, categories, user);
    }
}
