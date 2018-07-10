package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.alerts.AlertStatus;
import com.fxlabs.fxt.dao.entity.alerts.AlertType;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.es.AlertESRepository;
import com.fxlabs.fxt.dao.repository.jpa.SystemSettingRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.task.EmailTask;
import com.fxlabs.fxt.services.alerts.AlertService;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class NaaSTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlertESRepository alertESRepository;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AmqpClientService amqpClientService;

    @Autowired
    private UsersRepository usersRepository;

    @Value("${fx.naas.queue.routingkey}")
    private String naaSQueue;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    /**
     * List Alerts of type ERROR and UNREAD
     * Send as NaaSTask
     */
    public void process() {
        return ;
        /*
        Stream<Alert> alerts = null;//alertESRepository.findByTypeAndStatus(AlertType.ERROR, AlertStatus.UNREAD);
        alerts.forEach(alert -> {
            try {
                EmailTask task = new EmailTask();

                task.setSubject(alert.getSubject());
                StringBuilder sb = new StringBuilder();
                sb

                        .append("Component: ").append(alert.getRefType()).append("\n")
                        .append("Name: ").append(alert.getRefName()).append("\n")
                        .append("ID: ").append(alert.getRefId()).append("\n")
                        .append("Type: ").append(alert.getType()).append("\n")
                        .append("Task-Type: ").append(alert.getTaskType()).append("\n")
                        //.append("State: ").append(alert.getState()).append("\n")
                        .append("Logs: ").append(alert.getMessage()).append("\n");

                task.setBody(sb.toString());
                List<String> tos = new ArrayList<>();

                alert.getUsers().stream().forEach(u -> {
                    Optional<Users> usersOptional = usersRepository.findById(u);
                    if (usersOptional.isPresent()) {
                        tos.add(usersOptional.get().getEmail());
                    }
                });


                task.setTos(tos);

                amqpClientService.sendTask(task, naaSQueue);
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
            }

        });*/
    }

}
