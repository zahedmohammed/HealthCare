package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.project.JobNotification;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.notify.Notification;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.notify.NotificationService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class MarkTimeoutTaskProcessor {

    private static final String COLON = " : ";
    public static final String LINE_SEPERATOR = "\n";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    private TestSuiteResponseService testSuiteResponseService;

    @Autowired
    private NotificationService notificationAccountService;

    @Autowired
    private AmqpClientService amqpClientService;

    @Value("${fx.notification.slack.queue.routingkey}")
    private String slackNotificationQueue;

    @Autowired
    private TextEncryptor encryptor;

    @Autowired
    private LocalEventPublisher localEventPublisher;

    /**
     * Find Tasks with state 'PROCESSING'
     * If timeout >= NOW
     * Mark task TIMEOUT or add to description
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
        Date dt = DateUtils.addMinutes(new Date(), -60);
        Stream<Run> runs = runRepository.findByTaskStatusAndCreatedDateLessThan(TaskStatus.PROCESSING, dt);

        runs.forEach(run -> {
            try {
                Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                run.getTask().setStatus(TaskStatus.TIMEOUT);
                if (count.isPresent() && count.get() >= run.getTask().getTotalTests()) {
                    // count total-suites
                    // count total-tests
                    // count total-fail
                    // count total-time
                    run.getTask().setTotalSuiteCompleted(count.get());
                    run.getTask().setFailedTests(testSuiteResponseService.failedSum(run.getId()));
                    run.getTask().setTotalTestCompleted(testSuiteResponseService.passedSum(run.getId()));
                    run.getTask().setTotalTime(testSuiteResponseService.timeSum(run.getId()));
                }
                runRepository.saveAndFlush(run);
                sendNotification(run);

                // TODO - Test-Suites
            } catch (Exception ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }

            try {
                projectSyncEvent(run.getJob(), Status.Done, Entity.Job, run.getId(), run.getId(), run.getRunId());
            } catch (Exception ex) {
                logger.warn(ex.getLocalizedMessage());
            }

        });
    }


    private void sendNotification(Run run) {
        try {
            if (CollectionUtils.isEmpty(run.getJob().getNotifications())) {
                logger.debug("No notification for job [{}]", run.getJob().getName());
                return;
            }

            for (JobNotification jn : run.getJob().getNotifications()) {


                if (StringUtils.isEmpty(jn.getName()) || StringUtils.isEmpty(run.getJob().getCreatedBy())) {
                    logger.info("Ignoring notification invalid data");
                    return;
                }

                Response<Notification> notificationResponse = notificationAccountService.findByName(jn.getName(), run.getJob().getProject().getOrg().getName());

                if (notificationResponse.isErrors() || notificationResponse.getData() == null) {
                    logger.info("Notification not found for name [{}]", jn.getName());
                    return;
                }
                NotificationTask task = new NotificationTask();
                task.setId(run.getId());
                Map<String, String> opts = new HashMap<>();
                switch (notificationResponse.getData().getAccount().getAccountType()) {
                    case Slack:
                        if (notificationResponse.getData().getAccount() == null || StringUtils.isEmpty(notificationResponse.getData().getAccount().getAccessKey())) {
                            logger.info("Notification Token not found for account [{}]", notificationResponse.getData().getId());
                            break;
                        }
                        Optional<Account> accountOptional = accountRepository.findById(notificationResponse.getData().getAccount().getId());
                        Account account = accountOptional.isPresent() ? accountOptional.get() : null;

                        String token = account.getSecretKey();
                        if (!StringUtils.isEmpty(token)) {
                            token = encryptor.decrypt(token);
                        }
                        opts.put("TOKEN", token);


                        opts.put("MESSAGE", formatSlackMessage(run));
                        opts.put("CHANNELS", notificationResponse.getData().getChannel());
                        task.setOpts(opts);
                        amqpClientService.sendTask(task, slackNotificationQueue);
                        break;
                    case Email:
                        logger.info("Notification Account Type email not supported");
                        break;

                    default:
                        logger.info("Notification Account type [{}] not supported", notificationResponse.getData().getType());
                }
            }

        } catch (Exception e) {
            logger.info("Failed to send notification for Job [{}]", run.getJob().getName());
            logger.info(e.getLocalizedMessage());
        }

    }

    private String formatSlackMessage(Run run) {

        StringBuilder sb = new StringBuilder();

        sb.append("Job Name").append(COLON).append(run.getJob().getName()).append(LINE_SEPERATOR)
                .append("Description").append(COLON).append(getValue(run.getTask().getDescription())).append(LINE_SEPERATOR)
                .append("Status").append(COLON).append(run.getTask().getStatus().toString()).append(LINE_SEPERATOR)
                .append("Total Test Cases").append(COLON).append(run.getTask().getTotalTests()).append(LINE_SEPERATOR)
                .append("Failed Tests").append(COLON).append(run.getTask().getFailedTests()).append(LINE_SEPERATOR)
                .append("Skipper Tests").append(COLON).append(run.getTask().getSkippedTests()).append(LINE_SEPERATOR)
                .append("Total Tests Completed").append(COLON).append(run.getTask().getTotalTestCompleted()).append(LINE_SEPERATOR)
                .append("Total Bytes").append(COLON).append(run.getTask().getTotalBytes()).append(LINE_SEPERATOR)
                .append("Total Time").append(COLON).append(run.getTask().getTotalTime()).append(LINE_SEPERATOR)
                .append("Bytes").append(COLON).append(run.getTask().getTotalBytes()).append(LINE_SEPERATOR)
                .append("Region").append(COLON).append(run.getJob().getRegions());

        return sb.toString();
    }

    private static String getValue(String value) {

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return "";
        }

        return value;
    }

    public void projectSyncEvent(Job job, Status status, Entity entityType, String taskId, String runId, long runNumber) {

        if (job == null || status == null || entityType == null) {

            logger.info("Invalid event for project sync");
            return;
        }


        Event event = new Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(job.getProject().getName() + "/" + job.getName() + "/" + runNumber);
        event.setUser(job.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(Type.Run);
        event.setEntityId(job.getId());
        event.setLink("/app/projects/" + job.getProject().getId() + "/jobs/" + job.getId() + "/runs/" + runId);

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(job.getProject().getOrg().getName());
        org.setId(job.getProject().getOrg().getId());
        event.setOrg(org);


        logger.info("Sending event for publish on job [{}] and status [{}] for task type [{}]", job.getId(), status.toString(), event.getEventType());
        localEventPublisher.publish(event);
    }
}
