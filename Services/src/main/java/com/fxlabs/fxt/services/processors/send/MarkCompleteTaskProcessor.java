package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.notify.NotificationAccount;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.notify.NotificationAccountService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;
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
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class MarkCompleteTaskProcessor {

    private static final String COLON = " : ";
    public static final String LINE_SEPERATOR = "\n";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private TestSuiteResponseService testSuiteResponseService;

    @Autowired
    private AmqpClientService amqpClientService;

    @Autowired
    private NotificationAccountService notificationAccountService;
    // fx-notification-slack
    @Value("${fx.notification.slack.queue.routingkey}")
    private String slackNotificationQueue;

    /**
     * Find Tasks with state 'PROCESSING'
     * If completed-suites >= total-suites
     * Mark task complete
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
        try {
            Date dt = DateUtils.addMinutes(new Date(), -30);
            Stream<Run> runs = runRepository.findByTaskStatusAndCreatedDateGreaterThan(TaskStatus.PROCESSING, dt);


            runs.forEach(run -> {
                try {
                    //Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                    Long failed = testSuiteResponseService.failedSum(run.getId());
                    Long passed = testSuiteResponseService.passedSum(run.getId());
                    Long time = testSuiteResponseService.timeSum(run.getId());
                    Long bytes = testSuiteResponseService.byteSum(run.getId());

                    Long count = failed + passed;
                    if (count >= run.getTask().getTotalTests()) {
                        run.getTask().setStatus(TaskStatus.COMPLETED);
                    }
                    // count total-suites
                    // count total-tests
                    // count total-fail
                    // count total-time
                    // count total-bytes
                    run.getTask().setTotalSuiteCompleted(count);

                    run.getTask().setFailedTests(failed);
                    run.getTask().setTotalTestCompleted(passed);
                    run.getTask().setTotalTime(time);
                    run.getTask().setTotalBytes(bytes);

                    runRepository.saveAndFlush(run);

                    if (run.getTask().getStatus().equals(TaskStatus.COMPLETED)) {
                        sendNotification(run);
                    }


                    // TODO - Test-Suites
                } catch (RuntimeException ex) {
                    logger.warn(ex.getLocalizedMessage(), ex);
                }
            });
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
    }

    private void sendNotification(Run run) {
        try {
            if (CollectionUtils.isEmpty(run.getJob().getNotifications())) {
                logger.debug("No notification for job [{}]", run.getJob().getName());
                return;
            }

            for (String address : run.getJob().getNotifications()) {


                if (StringUtils.isEmpty(address) || StringUtils.isEmpty(run.getJob().getCreatedBy())) {
                    logger.info("Ignoring notification invalid data");
                    continue;
                }

                Response<NotificationAccount> notificationAccount = notificationAccountService.findByName(address, run.getJob().getCreatedBy());

                if (notificationAccount.isErrors() || notificationAccount.getData() == null) {
                    logger.info("Notification Account not found for name [{}]", address);
                    continue;
                }
                NotificationTask task = new NotificationTask();
                task.setId(run.getId());
                Map<String, String> opts = new HashMap<>();
                switch (notificationAccount.getData().getType()) {
                    case SLACK:
                        if (StringUtils.isEmpty(notificationAccount.getData().getToken())) {
                            logger.info("Notification Token not found for account [{}]", notificationAccount.getData().getId());
                            break;
                        }
                        opts.put("TOKEN", notificationAccount.getData().getToken());
                        opts.put("MESSAGE", formatSlackMessage(run));
                        opts.put("CHANNELS", notificationAccount.getData().getToken());
                        task.setOpts(opts);
                        amqpClientService.sendTask(task, slackNotificationQueue);
                        break;
                    case EMAIL:
                        logger.info("Notification Account Type email not supported");
                        break;

                    default:
                        logger.info("Notification Account type [{}] not supported", notificationAccount.getData().getType());
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
}


