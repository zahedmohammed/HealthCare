package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.run.RunService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public void process() {
        Date end = new Date();
        Date start = DateUtils.addMinutes(end, -5);
        Stream<Job> jobs = this.jobRepository.findByNextFireBetween(start, end);

        jobs.forEach(job -> {
            try {
                logger.info("Scheduling job [{}] name [{}] project [{}]", job.getId(), job.getName(), job.getProjectId());
                Response<com.fxlabs.fxt.dto.run.Run> runResponse = runService.run(job.getId(), null, null, null, null);
                if (runResponse.isErrors()) {
                    for (Message m : runResponse.getMessages())
                        logger.warn(m.getValue());
                }
                jobRepository.saveAndFlush(job);
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }

        });
    }
}
