package com.fxlabs.fxt.notification.slack.skill;

import com.fxlabs.fxt.notification.skill.services.NotificationService;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class SlackNotificationService implements NotificationService {


    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();



    /**
     * <p>
     *  This method does only one thing.
     *   1. Checkout the repository files to the given location.
     *
     *   At the end of the execution all the project files should in the given path.
     *   Or the response should indicate the processing failed by setting the Response.success to false along with the
     *   error message.
     * </p>
     *
     * @param task
     *  <p>
     *      Contains Slack System connection information.
     *      e.g.
     *      url - contains the IssueTracker endpoint
     *      username - IssueTracker username/access-key
     *      password - IssueTracker password/secret-key
     *  </p>
     *
     *
     * @return void
     *
     */
    @Override
    public void process(final NotificationTask task) {
        logger.info("In Slack NotificationSetcive for taskid [{}]", task.getId());

        SlackSession session = null;
        try {
            if (task == null || CollectionUtils.isEmpty(task.getOpts())) {
                logger.info("Options empty for task id : [{}]", task.getId());
                return;
            }

            String token = getToken(task.getOpts());

            if (StringUtils.isEmpty(token)){
                logger.info("Slack  TOKEN not found for task id : [{}]", task.getId());
                return;
            }

            String message = getMessage(task.getOpts());

            if (StringUtils.isEmpty(token)){
                logger.info("Slack  MESSAGE not found for task id : [{}]", task.getId());
                return;
            }


            String channel = getChannel(task.getOpts());

            if (StringUtils.isEmpty(channel)){
                logger.info("Slack channel  not found for task id : [{}]", task.getId());
                return;
            }



            session = SlackSessionFactory.createWebSocketSlackSession(token);
            session.connect();
            SlackChannel channel_ = session.findChannelByName(channel); //make sure bot is a member of the channel.
            session.sendMessage(channel_, message);
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        } finally {
            if (session != null) {
                try {
                    session.disconnect();
                } catch (IOException e) {
                    logger.warn(e.getLocalizedMessage(), e);
                }
            }
        }

    }

    private String getMessage(Map<String, String> opts) {
        String value = opts.get("MESSAGE");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }

    private String getToken(Map<String, String> opts) {

        String value = opts.get("TOKEN");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }


    private String getChannel(Map<String, String> opts) {

        String value = opts.get("CHANNELS");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }
//    public static void main(String[] args){
//        SlackSession session = null;
//        try {
//            session = SlackSessionFactory.createWebSocketSlackSession("xoxp-340960826822-339431155488-340132665426-093fffdddc63e0249d90526711bf7007");
//            session.connect();
//            SlackChannel channel = session.findChannelByName("random"); //make sure bot is a member of the channel.
//            session.sendMessage(channel, "hi im a bot");
//        } catch (Exception ex) {
//         System.out.print(ex.getLocalizedMessage());
//        } finally {
//            if (session != null) {
//                try {
//                    session.disconnect();
//                } catch (IOException e) {
//                    System.out.print("Close connection");
//                    System.out.print(e.getLocalizedMessage());
//                }
//            }
//        }
//    }


}
