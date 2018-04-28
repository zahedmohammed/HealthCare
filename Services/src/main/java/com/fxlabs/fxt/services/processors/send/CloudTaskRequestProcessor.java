package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectGitAccount;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import com.fxlabs.fxt.dao.entity.skills.Skill;
import com.fxlabs.fxt.dao.entity.skills.SkillSubscription;
import com.fxlabs.fxt.dao.entity.skills.SubscriptionTask;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
