package com.fxlabs.issues.services.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
//@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='true'")
public class JobConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /*@Bean(name = "botJobDetail")
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
    */
}
