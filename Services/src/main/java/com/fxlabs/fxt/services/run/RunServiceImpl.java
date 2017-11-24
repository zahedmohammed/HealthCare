package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.TestSuiteResponseRepository;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.processors.RunTaskRequestProcessor;
import com.fxlabs.fxt.services.project.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RunServiceImpl extends GenericServiceImpl<Run, com.fxlabs.fxt.dto.run.Run, String> implements RunService {

    private JobService projectJobService;
    private RunTaskRequestProcessor taskProcessor;
    private TestSuiteRepository projectDataSetRepository;
    private TestSuiteResponseRepository dataSetRepository;
    private TestSuiteResponseConverter dataSetConverter;

    @Autowired
    public RunServiceImpl(RunRepository repository, RunConverter converter, JobService projectJobService,
                          RunTaskRequestProcessor taskProcessor, TestSuiteRepository projectDataSetRepository,
                          TestSuiteResponseRepository dataSetRepository, TestSuiteResponseConverter dataSetConverter) {
        super(repository, converter);
        this.projectJobService = projectJobService;
        this.taskProcessor = taskProcessor;
        this.projectDataSetRepository = projectDataSetRepository;
        this.dataSetRepository = dataSetRepository;
        this.dataSetConverter = dataSetConverter;
    }


    public Response<com.fxlabs.fxt.dto.run.Run> run(String projectJob) {
        Response<Job> projectJobResponse = this.projectJobService.findById(projectJob);

        // Create Run
        com.fxlabs.fxt.dto.run.Run run = new com.fxlabs.fxt.dto.run.Run();
        run.setProjectJob(projectJobResponse.getData());
        // read last run and
        Long maxId = ( (RunRepository) repository).findMaxRunId(projectJob);
        if (maxId == null) {
            maxId = 0L;
        }
        run.setRunId(maxId + 1L);

        // Create Task
        RunTask task = new RunTask();
        task.setName(new Date().toString());
        task.setStatus("WAITING");
        task.setStartTime(new Date());

        // TODO - find total tests
        Long totalTests = projectDataSetRepository.countByProjectId(projectJobResponse.getData().getProject().getId());
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
