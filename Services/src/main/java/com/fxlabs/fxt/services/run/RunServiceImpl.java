package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.project.ProjectJob;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.processors.RunTaskProcessor;
import com.fxlabs.fxt.services.project.ProjectJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RunServiceImpl extends GenericServiceImpl<Run, com.fxlabs.fxt.dto.run.Run, String> implements RunService {

    private ProjectJobService projectJobService;
    private RunTaskProcessor taskProcessor;

    @Autowired
    public RunServiceImpl(RunRepository repository, RunConverter converter, ProjectJobService projectJobService, RunTaskProcessor taskProcessor) {
        super(repository, converter);
        this.projectJobService = projectJobService;
        this.taskProcessor = taskProcessor;
    }


    public Response<com.fxlabs.fxt.dto.run.Run> run(String projectJob) {
        Response<ProjectJob> projectJobResponse = this.projectJobService.findById(projectJob);

        // Create Run
        com.fxlabs.fxt.dto.run.Run run = new com.fxlabs.fxt.dto.run.Run();
        run.setProjectJob(projectJobResponse.getData());
        run.setRunId(1L);
        run.setStatus("In-progress");

        // Create Task
        RunTask task = new RunTask();
        task.setName("Fresh Run");
        task.setStatus("WAITING");
        task.setTotalTests(10L);

        List<RunTask> tasks = new ArrayList<>();
        tasks.add(task);
        run.setTasks(tasks);

        Response<com.fxlabs.fxt.dto.run.Run> response = save(run);

        taskProcessor.process(response.getData());

        // Copy ProjectDataSets to DataSets.


        return response;
    }

}
