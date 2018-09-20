package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.JobConverter;
import com.fxlabs.fxt.converters.project.JobIssueTrackerConverter;
import com.fxlabs.fxt.converters.project.JobNotificationConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.project.JobIssueTracker;
import com.fxlabs.fxt.dao.repository.jpa.JobIssueTrackerRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class JobServiceImpl extends GenericServiceImpl<Job, com.fxlabs.fxt.dto.project.Job, String> implements JobService {

    private JobRepository jobRepository;
    private ProjectService projectService;
    private RunRepository runRepository;
    private JobNotificationConverter jobNotificationConverter;
    private JobIssueTrackerConverter jobIssueTrackerConverter;

    //private JobNotificationRepository jobNotificationRepository;
    private JobIssueTrackerRepository jobIssueTrackerRepository;

    private TestCaseResponseITRepository testCaseResponseITRepository;

    @Autowired
    public JobServiceImpl(JobRepository repository, JobConverter converter, ProjectService projectService, RunRepository runRepository,
                          JobNotificationConverter projectNotificationConverter, JobIssueTrackerConverter jobIssueTrackerConverter,
                           JobIssueTrackerRepository jobIssueTrackerRepository, TestCaseResponseITRepository testCaseResponseITRepository) {
        super(repository, converter);
        this.jobRepository = repository;
        this.projectService = projectService;
        this.runRepository = runRepository;
        this.jobNotificationConverter = jobNotificationConverter;
        this.jobIssueTrackerConverter = jobIssueTrackerConverter;
        this.jobIssueTrackerRepository = jobIssueTrackerRepository;
        this.testCaseResponseITRepository = testCaseResponseITRepository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> save(List<com.fxlabs.fxt.dto.project.Job> jobs, String user) {
        if (CollectionUtils.isEmpty(jobs)) {
            throw new FxException("Invalid arguments");
        }
        final List<Message> messages = new ArrayList<>();
        Response response = new Response<>();
        jobs.stream().forEach(job -> {

           // getSavedProjectNotification(job);
            getSavedProjectIssueTracker(job);

            Response<com.fxlabs.fxt.dto.project.Job> jobResponse = save(job, user);
            if (!CollectionUtils.isEmpty(jobResponse.getMessages())) {
                messages.addAll(jobResponse.getMessages());
            }
            if (jobResponse.isErrors()) {
                response.withErrors(true);
            }
        });

        return response.withMessages(messages);
    }

//    private void getSavedProjectNotification(com.fxlabs.fxt.dto.project.Job job) {
//        if (job.getNotifications() != null) {
//            JobNotification projectNotification = projectNotificationConverter.convertToEntity(job.getNotification());
//            com.fxlabs.fxt.dto.project.JobNotification projectNotificationDto = projectNotificationConverter.convertToDto(projectNotificationRepository.save(projectNotification));
//            job.setNotification(projectNotificationDto);
//        }
//    }

    private void getSavedProjectIssueTracker(com.fxlabs.fxt.dto.project.Job job) {
        if (job.getIssueTracker() != null) {
            JobIssueTracker projectIssueTracker = jobIssueTrackerConverter.convertToEntity(job.getIssueTracker());
            com.fxlabs.fxt.dto.project.JobIssueTracker projectIssueTrackerDto = jobIssueTrackerConverter.convertToDto(jobIssueTrackerRepository.save(projectIssueTracker));
            job.setIssueTracker(projectIssueTrackerDto);
        }
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Job> save(com.fxlabs.fxt.dto.project.Job job, String user) {
        if (CronUtils.isValidCron(job.getCron())) {
            Date next = CronUtils.cronNext(job.getCron());
            job.setNextFire(next);
        }

        return super.save(job, user);
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findByProjectId(String projectId, String user, Pageable pageable) {
        // check user has access to project
        projectService.isUserEntitled(projectId, user);
        Page<Job> page = this.jobRepository.findByProjectIdAndInactive(projectId, false, pageable);
        List<com.fxlabs.fxt.dto.project.Job> jobList = converter.convertToDtos(page.getContent());
        jobList.forEach(job -> {
            if (CollectionUtils.isEmpty(job.getNotifications()) || job.getNotifications().get(0) == null &&  job.getNotifications().get(1) == null) {
                job.setNotificationToDo(true);
                return;
            }

            if (StringUtils.isEmpty(job.getNotifications().get(0).getTo()) && StringUtils.isEmpty(job.getNotifications().get(1).getChannel())) {
                job.setNotificationToDo(true);
                return;
            }
        });

        jobList.forEach(job -> {
            if (job.getIssueTracker() == null || StringUtils.isEmpty(job.getIssueTracker().getAccountType())){
                job.setIssueTrackerToDo(true);
            }else{
                String itId = job.getProject().getId() + "//" + job.getId() + "%"  ;
              //  long totalOpenIssues = testCaseResponseITRepository.countByStatusAndTestCaseResponseIssueTrackerIdLike("open",itId);
                long totalOpenIssues = testCaseResponseITRepository.countByStatusAndProjectIdAndJobId("open",job.getProject().getId(),job.getId());
             //   long totalClosedIssues = testCaseResponseITRepository.countByStatusAndTestCaseResponseIssueTrackerIdLike("closed",itId);
                long totalClosedIssues = testCaseResponseITRepository.countByStatusAndProjectIdAndJobId("closed",job.getProject().getId(),job.getId());

                job.setOpenIssues(totalOpenIssues);
                job.setClosedIssues(totalClosedIssues);
            }
        });

        return new Response<>(jobList, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> deleteByProjectId(String projectId, String user, Pageable pageable) {
        // check user has access to project
        Response<List<com.fxlabs.fxt.dto.project.Job>> jobs = findByProjectId(projectId, user, pageable);
        jobs.getData().stream().forEach(job -> {
            job.setInactive(true);
        });
        return save(jobs.getData(), user);
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findAll(String org, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner


        Response<List<Project>> projectsResponse = projectService.findProjects(org, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<List<com.fxlabs.fxt.dto.project.Job>>(Collections.emptyList(), 0L, 0);
        }


        final List<Job> jobs = new ArrayList<>();
        projectsResponse.getData().stream().forEach(p -> {
            Page<Job> _jobs = jobRepository.findByProjectIdAndInactive(p.getId(), false, PageRequest.of(0, 20));
            if (!CollectionUtils.isEmpty(_jobs.getContent())) {
                jobs.addAll(_jobs.getContent());
            }
        });

        List<com.fxlabs.fxt.dto.project.Job> jobList = converter.convertToDtos(jobs);
        jobList.forEach(job -> {
            if (CollectionUtils.isEmpty(job.getNotifications()) || job.getNotifications().get(0) == null &&  job.getNotifications().get(1) == null) {
                job.setNotificationToDo(true);
                return;
            }

            if (StringUtils.isEmpty(job.getNotifications().get(0).getTo()) && StringUtils.isEmpty(job.getNotifications().get(1).getChannel())) {
                job.setNotificationToDo(true);
                return;
            }
        });

        jobList.forEach(job -> {
            if (job.getIssueTracker() == null || StringUtils.isEmpty(job.getIssueTracker().getAccountType())){
                job.setIssueTrackerToDo(true);
            }
        });
        return new Response<>(jobList, new Long(jobs.size()), jobs.size());

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
            Long count = jobRepository.countByProjectIdAndInactive(p.getId(), false);
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findJobsByIssueTracker(String name) {
        List<Job> _jobs = jobRepository.findByIssueTrackerName(name);
        return new Response<>(converter.convertToDtos(_jobs), new Long(_jobs.size()), _jobs.size());
    }

    @Override
    public void isUserEntitled(String jobId, String user) {
        // TODO - user has access to job/project
        /*Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (!jobOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, jobId));
        }
        projectService.isUserEntitled(jobOptional.get().getProject().getId(), user);*/
    }

}
