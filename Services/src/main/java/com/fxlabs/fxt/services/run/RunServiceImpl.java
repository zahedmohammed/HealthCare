package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteResponseRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.run.*;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
    private SuiteESRepository suiteESRepository;
    private SuiteConverter suiteConverter;

    @Autowired
    public RunServiceImpl(RunRepository repository, RunConverter converter, JobService projectJobService,
                          /*RunTaskRequestProcessor taskProcessor, */TestSuiteRepository projectDataSetRepository,
                          TestSuiteResponseRepository dataSetRepository, TestSuiteResponseConverter dataSetConverter,
                          TestSuiteESRepository testSuiteESRepository, ProjectService projectService,
                          SuiteESRepository suiteESRepository, SuiteConverter suiteConverter) {
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
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobId(String jobId, String user, Pageable pageable) {
        jobService.isUserEntitled(jobId, user);
        Page<Run> page = ((RunRepository) repository).findByJobId(jobId, pageable);
        return new Response<List<com.fxlabs.fxt.dto.run.Run>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.run.Run> run(String jobId, String region, String tags, String env, String suites, String user) {
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
            String[] _tags = null;
            if (StringUtils.isNotEmpty(tags)) {
                attributes.put(RunConstants.TAGS, tags);
                _tags = StringUtils.split(tags, ",");
            }
            if (StringUtils.isNotEmpty(suites)) {
                attributes.put(RunConstants.SUITES, suites);
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
    public Response<List<TestSuiteResponse>> findByRunId(String runId, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseRepository.findByRunId(runId, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<TestSuiteResponse>> findByPk(String id, String name, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseRepository.findByRunIdAndTestSuite(id, name, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<TestSuiteResponse>> findByTestSuite(String testSuite, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.testSuiteResponseRepository.findByTestSuite(testSuite, pageable);

        List<TestSuiteResponse> dataSets = testSuiteResponseConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<List<Suite>> findSummaryByRunId(String runId, String user, Pageable pageable) {

        Page<com.fxlabs.fxt.dao.entity.run.Suite> page = this.suiteESRepository.findByRunId(runId, pageable);

        List<Suite> dataSets = suiteConverter.convertToDtos(page.getContent());
        return new Response<List<Suite>>(dataSets, page.getTotalElements(), page.getTotalPages());
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
            Long count = repository.countByJobProjectId(p.getId());
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());
    }

    @Override
    public Response<Long> countTests(String user, Pageable pageable) {
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
            Long count = repository.countTestsByProject(p.getId());
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
            Long count = repository.countBytesByProject(p.getId());
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

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
            Long count = repository.countTimeByProject(p.getId());
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

}
