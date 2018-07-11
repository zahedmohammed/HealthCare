package com.fxlabs.fxt.services.job;

import com.fxlabs.fxt.services.processors.send.*;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean(name = "botJobDetail")
    public JobDetail jobDetail() {
        return newJob().ofType(SendRequestJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_RunRequestProcessor_Job_Detail")).withDescription("Invoke RunRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger trigger(@Qualifier("botJobDetail") JobDetail job) {

        int frequencyInSec = 10;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_RunRequestProcessor_Trigger")).withDescription("RunRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(frequencyInSec)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    @Bean(name = "gaaSJobDetail")
    public JobDetail gaaSJobDetail() {
        return newJob().ofType(SendGaaSTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_GaaSRequestProcessor_Job_Detail")).withDescription("Invoke GaaSRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger gaaSTrigger(@Qualifier("gaaSJobDetail") JobDetail job) {

        int frequencyInMins = 12 * 60;//Runs Every 12 hours
        logger.info("Configuring trigger to fire every {} mins", frequencyInMins);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_GaaSRequestProcessor_Trigger")).withDescription("GaaSRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(frequencyInMins)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    @Bean(name = "naaSJobDetail")
    public JobDetail naaSJobDetail() {
        return newJob().ofType(SendNaaSTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_NaaSRequestProcessor_Job_Detail")).withDescription("Invoke NaaSRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger naaSTrigger(@Qualifier("naaSJobDetail") JobDetail job) {

        int frequencyInMins = 5;
        logger.info("Configuring trigger to fire every {} mins", frequencyInMins);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_NaaSRequestProcessor_Trigger")).withDescription("NaaSRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(frequencyInMins)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    @Bean(name = "jobCronJobDetail")
    public JobDetail jobCronJobDetail() {
        return newJob().ofType(JobCronTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_JobCronRequestProcessor_Job_Detail")).withDescription("Invoke JobCronRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger jobCronTrigger(@Qualifier("jobCronJobDetail") JobDetail job) {

        int frequencyInMins = 4;
        logger.info("Configuring trigger to fire every {} mins", frequencyInMins);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_JobCronRequestProcessor_Trigger")).withDescription("JobCronRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(frequencyInMins)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    // Task complete job
    @Bean(name = "taskCompleteJobDetail")
    public JobDetail taskCompleteJobDetail() {
        return newJob().ofType(MarkCompleteTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_TaskCompleteRequestProcessor_Job_Detail")).withDescription("Invoke TaskCompleteProcessor Job service...").build();
    }

    @Bean
    public Trigger taskCompleteTrigger(@Qualifier("taskCompleteJobDetail") JobDetail job) {

        int frequencyInSecs = 15;
        logger.info("Configuring trigger to fire every {} secs", frequencyInSecs);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_TaskCompleteRequestProcessor_Trigger")).withDescription("TaskCompleteRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(frequencyInSecs)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    // Task timeout job
    @Bean(name = "taskTimeoutJobDetail")
    public JobDetail taskTimeoutJobDetail() {
        return newJob().ofType(MarkTimeoutTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_TaskTimeoutRequestProcessor_Job_Detail")).withDescription("Invoke TaskTimeoutRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger taskTimeoutTrigger(@Qualifier("taskTimeoutJobDetail") JobDetail job) {

        int frequencyInMins = 10;
        logger.info("Configuring trigger to fire every [{}] mins", frequencyInMins);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_TaskTimeoutRequestProcessor_Trigger")).withDescription("TaskTimeoutRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(frequencyInMins)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }

    // Recalculate/heal cron's next-fire event
    @Bean(name = "healNextFireJobDetail")
    public JobDetail healNextFireJobDetail() {
        return newJob().ofType(HealNextFireTaskJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_HealNextFireRequestProcessor_Job_Detail")).withDescription("Invoke HealNextFireRequestProcessor Job service...").build();
    }

    @Bean
    public Trigger healNextFireTrigger(@Qualifier("healNextFireJobDetail") JobDetail job) {

        int frequencyInMins = 10;
        logger.info("Configuring trigger to fire every [{}] mins", frequencyInMins);

        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_JobCronRequestProcessor_Trigger")).withDescription("JobCronRequestProcessor trigger")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(frequencyInMins)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithExistingCount()
                )
                .build();
    }
}
