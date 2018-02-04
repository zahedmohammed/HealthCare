package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectGitAccount;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import com.fxlabs.fxt.dao.repository.jpa.ProjectGitAccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GaaSTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectGitAccountRepository projectGitAccountRepository;

    @Autowired
    private AmqpClientService amqpClientService;

    @Autowired
    private BytesEncryptor encryptor;

    @Value("${fx.gaas.queue.routingkey}")
    private String gaaSQueue;

    /**
     * List projects of type GIT
     * Send as GitTask
     */
    public void process() {
        Stream<Project> projects = projectRepository.findByProjectTypeAndDeleted(ProjectType.GIT, false);
        projects.forEach(project -> {
            Optional<ProjectGitAccount> gitAccount = projectGitAccountRepository.findByProjectId(project.getId());
            if (!gitAccount.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] and Name [{}]", project.getId(), project.getName());
                return;
            }
            GitTask task = new GitTask();
            task.setProjectId(project.getId());
            task.setGitUrl(gitAccount.get().getUrl());
            task.setGitBranch(gitAccount.get().getBranch());
            task.setGitUsername(gitAccount.get().getUsername());
            if (!StringUtils.isEmpty(gitAccount.get().getPassword())) {
                task.setGitPassword(new String(this.encryptor.decrypt(gitAccount.get().getPassword().getBytes())));
            }
            task.setGitLastCommit(gitAccount.get().getLastCommit());

            amqpClientService.sendTask(task, gaaSQueue);

        });
    }

}
