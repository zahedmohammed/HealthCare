package com.fxlabs.fxt.services.users.job;

import com.fxlabs.fxt.services.processors.send.SendRequestJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
//@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='true'")
public class JobConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public JobDetail jobDetail() {

        return newJob().ofType(SendRequestJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_RunRequestProcessor_Job_Detail")).withDescription("Invoke RunRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {

        int frequencyInSec = 3;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_RunRequestProcessor_Trigger")).withDescription("RunRequestProcessor trigger").withSchedule(simpleSchedule().withIntervalInSeconds(frequencyInSec).repeatForever()).build();
    }
}
