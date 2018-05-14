package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class CloudTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmqpClientService amqpClientService;

    //@Autowired
    //private TextEncryptor encryptor;

    /**
     * Sends CloudTask
     */
//    public void process(SubscriptionTask task) {
//        SkillSubscription subscription = task.getSubscription();
//       // Skill skill = subscription.getSkill();
//        // TODO - read [queue, url, u, p, image, size] - Skill
//        // TODO - read [task-id, count, group-name, connect-queue, cmd] -> Subscription
//
//        CloudTask cloudTask = new CloudTask();
//
//        cloudTask.setId(task.getId());
//
//        cloudTask.getOpts().put("URL", skill.getHost());
//        cloudTask.getOpts().put("USERNAME", skill.getAccessKey());
//        cloudTask.getOpts().put("PASSWORD", skill.getSecretKey());
//        cloudTask.getOpts().put("IMAGE", skill.getProp1());
//        cloudTask.getOpts().put("SIZE", skill.getProp2());
//
//        cloudTask.getOpts().put("COUNT", subscription.getProp1());
//        cloudTask.getOpts().put("GROUP", subscription.getProp2());
//
//        // TODO
//        //cloudTask.getOpts().put("INIT", );
//
//       // amqpClientService.sendTask(cloudTask, skill.getKey());
//
//    }

}
