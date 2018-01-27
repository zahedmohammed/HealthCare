package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteResponseRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.run.RunConstants;
import com.fxlabs.fxt.dto.run.TaskStatus;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.processors.send.RunTaskRequestProcessor;
import com.fxlabs.fxt.services.project.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RunServiceImpl extends GenericServiceImpl<Run, com.fxlabs.fxt.dto.run.Run, String> implements RunService {

    private JobService projectJobService;
    //private RunTaskRequestProcessor taskProcessor;
    private TestSuiteRepository projectDataSetRepository;
    private TestSuiteResponseRepository dataSetRepository;
    private TestSuiteResponseConverter dataSetConverter;

    @Autowired
    public RunServiceImpl(RunRepository repository, RunConverter converter, JobService projectJobService,
                          /*RunTaskRequestProcessor taskProcessor, */TestSuiteRepository projectDataSetRepository,
                          TestSuiteResponseRepository dataSetRepository, TestSuiteResponseConverter dataSetConverter) {
        super(repository, converter);
        this.projectJobService = projectJobService;
        //this.taskProcessor = taskProcessor;
        this.projectDataSetRepository = projectDataSetRepository;
        this.dataSetRepository = dataSetRepository;
        this.dataSetConverter = dataSetConverter;
    }


    public Response<com.fxlabs.fxt.dto.run.Run> run(String jobId, String region, String tags, String env) {
        Response<Job> jobResponse = this.projectJobService.findById(jobId);

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
        run.setAttributes(attributes);

        // Create Task
        RunTask task = new RunTask();
        task.setName(new Date().toString());
        task.setStatus(TaskStatus.WAITING);
        task.setStartTime(new Date());

        // TODO - find total tests
        Long totalTests = projectDataSetRepository.countByProjectIdAndType(jobResponse.getData().getProject().getId(), TestSuiteType.SUITE);
        task.setTotalTests(totalTests);

        run.setTask(task);

        Response<com.fxlabs.fxt.dto.run.Run> response = save(run);

        //taskProcessor.process(response.getData());

        // Copy ProjectDataSets to DataSets.


        return response;
    }

    public Response<List<TestSuiteResponse>> findByRunId(String runId, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.run.TestSuiteResponse> page = this.dataSetRepository.findByRunId(runId, pageable);

        List<TestSuiteResponse> dataSets = dataSetConverter.convertToDtos(page.getContent());
        return new Response<List<TestSuiteResponse>>(dataSets, page.getTotalElements(), page.getTotalPages());
    }

}
