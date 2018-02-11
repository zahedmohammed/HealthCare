package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.services.project.CronUtils;
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
public class HealNextFireTaskProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobRepository jobRepository;

    /**
     * Find Jobs with next-fire <= 10 mins
     * Calculate next-fire
     */
    public void process() {
        Date dt = DateUtils.addMinutes(new Date(), -10);
        Stream<Job> jobs = this.jobRepository.findByNextFireLessThanAndInactive(dt, false);
        jobs.forEach(job -> {
            try {
                logger.info("Healing next-fire for job [{}] name [{}] project [{}]", job.getId(), job.getName(), job.getProject().getId());
                job.setNextFire(CronUtils.cronNext(job.getCron()));
                jobRepository.saveAndFlush(job);
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }
}
