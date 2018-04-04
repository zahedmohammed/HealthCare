package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.notify.NotificationAccount;
import com.fxlabs.fxt.dto.notify.NotificationType;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.notify.NotificationAccountService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.tasks.Task;
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

    @Value("${fx.notification.slack.queue.routingkey}")
    private String slackNotificationQueue;

    /**
     * Find Tasks with state 'PROCESSING'
     * If completed-suites >= total-suites
     * Mark task complete
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
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
    }

    private void sendNotification(Run run){

        if (CollectionUtils.isEmpty(run.getJob().getNotifications())){
            logger.debug("No notification for job [{}]", run.getJob().getName());
            return;
        }

        for (String address :run.getJob().getNotifications()){
            Response<NotificationAccount> notificationAccount = notificationAccountService.findByName(address, run.getJob().getCreatedBy());

            if (notificationAccount.isErrors() || notificationAccount.getData() == null) {
                logger.info("Notification Account not found for name [{}]", address);
                return;
            }
            NotificationTask task = new NotificationTask();
            task.setId(run.getId());
            Map<String, String> opts = new HashMap<>();
            switch (notificationAccount.getData().getType()){
                case SLACK:
                    if (StringUtils.isEmpty(notificationAccount.getData().getToken())) {
                        logger.info("Notification Token not found for account [{}]", notificationAccount.getData().getId());
                        break;
                    }
                    opts.put("TOKEN", notificationAccount.getData().getToken());
                    opts.put("MESSAGE", formatSlackMessage(run));
                    amqpClientService.sendTask(task, slackNotificationQueue);
                    break;
                case EMAIL:
                    logger.info("Notification Account Type email not supported");
                    break;

                    default:
                        logger.info("Notification Account type [{}] not supported", notificationAccount.getData().getType());
            }


        }

    }


    private String formatSlackMessage(Run run) {

        StringBuilder sb = new StringBuilder();

        sb.append("Job Name").append(COLON).append(run.getJob().getName()).append(LINE_SEPERATOR)
                .append("Status").append(COLON).append(run.getTask().getStatus().toString()).append(LINE_SEPERATOR)
                .append("Total Test Cases").append(COLON).append(run.getTask().getTotalTests()).append(LINE_SEPERATOR)
                .append("Failed").append(COLON).append(run.getTask().getFailedTests()).append(LINE_SEPERATOR)
                .append("Bytes").append(COLON).append(run.getTask().getTotalBytes()).append(LINE_SEPERATOR)
                .append("Region").append(run.getJob().getRegions());

        return sb.toString();
    }
}
