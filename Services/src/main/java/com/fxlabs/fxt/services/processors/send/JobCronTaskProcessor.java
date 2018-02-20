package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectUsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.project.CronUtils;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.run.RunService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class JobCronTaskProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RunService runService;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ProjectUsersRepository projectUsersRepository;

    public void process() {
        Date end = new Date();
        Date start = DateUtils.addMinutes(end, -5);
        Stream<Job> jobs = this.jobRepository.findByNextFireBetweenAndInactive(start, end, false);
        jobs.forEach(job -> {
            try {
                logger.info("Scheduling job [{}] name [{}] project [{}]", job.getId(), job.getName(), job.getProject().getId());

                if (CronUtils.isValidCron(job.getCron())) {
                    job.setNextFire(CronUtils.cronNext(job.getCron()));
                    jobRepository.saveAndFlush(job);
                }

                List<ProjectUsers> projectUsers = projectUsersRepository.findByProjectIdAndRole(job.getProject().getId(), ProjectRole.OWNER);
                if (CollectionUtils.isEmpty(projectUsers)) {
                    logger.warn("Skipping job run. No Owners found for the project with name [{}] and id [{}]", job.getProject().getName(), job.getProject().getId());
                    return;
                }
                Users users = projectUsers.get(0).getUsers();
                Response<com.fxlabs.fxt.dto.run.Run> runResponse = runService.run(job.getId(), null, null, null, null, users.getId());
                if (runResponse.isErrors()) {
                    for (Message m : runResponse.getMessages())
                        logger.warn(m.getValue());
                }
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }
}
