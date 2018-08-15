package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.JobNotification;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.notify.Notification;
import com.fxlabs.fxt.dto.task.EmailTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.notify.NotificationService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import com.fxlabs.fxt.services.users.SystemSettingService;
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

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional(rollbackFor = Exception.class)
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
    private NotificationService notificationAccountService;
    // fx-notification-slack
    @Value("${fx.notification.slack.queue.routingkey}")
    private String slackNotificationQueue;
    // fx-naas
    @Value("${fx.naas.queue.routingkey}")
    private String naaSQueue;

    @Autowired
    private TextEncryptor encryptor;

    @Autowired
    private SystemSettingService systemSettingService;

    private final static Collection<TaskStatus> statuses = Arrays.asList(TaskStatus.PROCESSING, TaskStatus.COMPLETED);

    /**
     * Find Tasks with state 'PROCESSING'
     * If completed-suites >= total-suites
     * Mark task complete
     * Calculate total-time, total-completed, total-failed etc
     */
    public void process() {
        try {
            Date dt = DateUtils.addMinutes(new Date(), -30);
            Stream<Run> runs = runRepository.findByTaskStatusInAndCreatedDateGreaterThan(statuses, dt);


            runs.forEach(run -> {
                try {
                    //Optional<Long> count = testSuiteResponseESRepository.countByRunId(run.getId());
                    Long failed = testSuiteResponseService.failedSum(run.getId());
                    Long passed = testSuiteResponseService.passedSum(run.getId());
                    Long time = testSuiteResponseService.timeSum(run.getId());
                    Long bytes = testSuiteResponseService.byteSum(run.getId());

                    Map<String, Long> statsMap = testSuiteResponseService.runStats(run.getId());

                    TaskStatus oldStatus = run.getTask().getStatus();
                    Long count = failed + passed;
                    if (count >= run.getTask().getTotalTests()) {
                        run.getTask().setTotalTests(count);
                        run.getTask().setEndTime(new Date());
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

                    run.setStats(statsMap);

                    runRepository.saveAndFlush(run);

                    if (run.getTask().getStatus().equals(TaskStatus.COMPLETED) && !oldStatus.equals(TaskStatus.COMPLETED)) {
                        sendNotification(run);
                    }


                    // TODO - Test-Suites
                } catch (Exception ex) {
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

            for (JobNotification jn : run.getJob().getNotifications()) {

//                if (StringUtils.isEmpty(jn.getName()) || StringUtils.isEmpty(run.getJob().getCreatedBy())) {
//                    logger.info("Ignoring notification invalid data");
//                    return;
//                }

                if (! org.apache.commons.lang3.StringUtils.isBlank(jn.getTo())){

                    // Project-name, Job-name, Run-Id, Status, Pass/Fail and Region etc.
                    StringBuilder subjet = new StringBuilder();

                    subjet.append("#"+run.getRunId()+". ");
//                    subjet.append("(");
                    subjet.append(run.getJob().getProject().getName());
                    subjet.append(" > ");
                    subjet.append(run.getJob().getName());
//                    subjet.append(")");
                    subjet.append(" - ");
                    subjet.append(run.getTask().getTotalTestCompleted()+"(passed)");
                    subjet.append(" / ");
                    subjet.append(run.getTask().getFailedTests()+"(failed)");
                    subjet.append(" - ");
                    subjet.append("Status: "+run.getTask().getStatus().toString());
                    subjet.append(" - ");
                    subjet.append("Region: "+run.getJob().getRegions());

                    sendEmailTask(jn.getTo(), subjet.toString(), formatBody(run));
                }

                //TODO: Slack notification

//                Response<Notification> notificationResponse = notificationAccountService.findByName(jn.getName(), run.getJob().getProject().getOrg().getName());
//
//                if (notificationResponse.isErrors() || notificationResponse.getData() == null) {
//                    logger.info("Notification Account not found for name [{}]", jn.getName());
//                    return;
//                }
//
//                Notification notification = notificationResponse.getData();
//                NotificationTask task = new NotificationTask();
//                task.setId(run.getId());
//                Map<String, String> opts = new HashMap<>();
//                switch (notification.getAccount().getAccountType()) {
//                    case Slack:
//                        if (notification.getAccount() == null || StringUtils.isEmpty(notification.getAccount().getSecretKey())) {
//                            logger.info("Notification Token not found for account [{}]", notification.getId());
//                            break;
//                        }
//                        String token = notification.getAccount().getSecretKey();
//                        token = encryptor.decrypt(token);
//                        opts.put("TOKEN", token);
//                        opts.put("MESSAGE", formatBody(run));
//                        opts.put("CHANNELS", notification.getChannel());
//                        task.setOpts(opts);
//                        amqpClientService.sendTask(task, slackNotificationQueue);
//                        break;
//                    case Email:
//                        //logger.info("Notification Account Type email not supported");
//                        sendEmailTask(notification.getChannel(), "Job #" + run.getRunId(), formatBody(run));
//                        break;
//
//                    default:
//                        logger.info("Notification Account type [{}] not supported", notification.getType());
//                }
            }

        } catch (Exception e) {
            logger.info("Failed to send notification for Job [{}]", run.getJob().getName());
            logger.info(e.getLocalizedMessage());
        }

    }


    private void sendEmailTask(String to, String subject, String body) {
        try {
            EmailTask task = new EmailTask();

            task.setSubject(subject);
            task.setBody(body);
            List<String> tos = Arrays.asList(to);
            task.setTos(tos);
            amqpClientService.sendTask(task, naaSQueue);
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
        }
    }

    private static final String REPORT_URL = "https://%s/#/app/jobs/%s/runs/%s";

    private String formatBody(Run run) {

        StringBuilder sb = new StringBuilder();

        String host = systemSettingService.findByKey(SystemSettingService.FX_HOST);
        String report = String.format(REPORT_URL, host, run.getJob().getId(), run.getId());

        sb
                .append("----").append(LINE_SEPERATOR)
                .append("Job Name").append(COLON).append(run.getJob().getName()).append(LINE_SEPERATOR)
                .append("Run ID").append(COLON).append(run.getRunId()).append(LINE_SEPERATOR)
                .append("Date").append(COLON).append(run.getCreatedDate()).append(LINE_SEPERATOR)
                .append("User").append(COLON).append(run.getCreatedBy()).append(LINE_SEPERATOR)
                .append("Description").append(COLON).append(getValue(run.getTask().getDescription())).append(LINE_SEPERATOR)
                .append("Status").append(COLON).append(run.getTask().getStatus().toString()).append(LINE_SEPERATOR)
                .append("Total Test Cases").append(COLON).append(run.getTask().getTotalTests()).append(LINE_SEPERATOR)
                .append("Failed Tests").append(COLON).append(run.getTask().getFailedTests()).append(LINE_SEPERATOR)
                .append("Skipper Tests").append(COLON).append(run.getTask().getSkippedTests()).append(LINE_SEPERATOR)
                .append("Total Tests Completed").append(COLON).append(run.getTask().getTotalTestCompleted()).append(LINE_SEPERATOR)
                .append("Total Bytes").append(COLON).append(run.getTask().getTotalBytes()).append(LINE_SEPERATOR)
                .append("Total Time").append(COLON).append(run.getTask().getTotalTime()).append(LINE_SEPERATOR)
                .append("Bytes").append(COLON).append(run.getTask().getTotalBytes()).append(LINE_SEPERATOR)
                .append("Region").append(COLON).append(run.getJob().getRegions()).append(LINE_SEPERATOR)
                .append("Report").append(COLON).append(report).append(LINE_SEPERATOR)
                .append("--- FX Bot ---");


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


