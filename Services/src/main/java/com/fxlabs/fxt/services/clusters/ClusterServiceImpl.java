package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.ClusterConverter;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import com.fxlabs.fxt.dao.repository.es.ClusterESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskType;
import com.fxlabs.fxt.dto.cloud.PingTask;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.clusters.AccountType;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.clusters.ClusterStatus;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ClusterServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.clusters.Cluster, Cluster, String> implements ClusterService {

    private ClusterRepository clusterRepository;
    private ClusterESRepository clusterESRepository;
    private ClusterConverter clusterConverter;
    private AccountRepository accountRepository;
    private AmqpAdmin amqpAdmin;
    private TopicExchange topicExchange;
    private OrgUsersRepository orgUsersRepository;
    private SubscriptionTaskRepository subscriptionTaskRepository;
    private AmqpClientService amqpClientService;
    private String fxExecutionBotScriptUrl;
    private String fxDefaultResponseKey;
    private String fxUserName;
    private String fxPassword;
    private String fxPort;
    private String fxHost;
    private SystemSettingRepository systemSettingRepository;
    private static String SPACE = " ";

    @Autowired
    public ClusterServiceImpl(ClusterRepository clusterRepository, ClusterESRepository clusterESRepository, AccountRepository accountRepository,
                              ClusterConverter clusterConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange,
                              OrgUsersRepository orgUsersRepository, @Value("${fx.default.response.queue.routingkey}") String fxDefaultResponseKey,
                              UsersRepository usersRepository, @Value("${fx.execution.bot.install.script.url}") String fxExecutionBotScriptUrl,
                              AmqpClientService amqpClientService, SubscriptionTaskRepository subscriptionTaskRepository,
                              @Value("${spring.rabbitmq.username}") String fxUserName, @Value("${spring.rabbitmq.password}") String fxPassword,
                              @Value("${spring.rabbitmq.port}") String fxPort, @Value("${spring.rabbitmq.host}") String fxHost,
                              SystemSettingRepository systemSettingRepository) {

        super(clusterRepository, clusterConverter);

        this.clusterRepository = clusterRepository;
        this.clusterESRepository = clusterESRepository;
        this.clusterConverter = clusterConverter;
        this.accountRepository = accountRepository;
        this.amqpAdmin = amqpAdmin;
        this.topicExchange = topicExchange;
        this.orgUsersRepository = orgUsersRepository;
        this.subscriptionTaskRepository = subscriptionTaskRepository;
        this.fxExecutionBotScriptUrl = fxExecutionBotScriptUrl;
        this.fxDefaultResponseKey = fxDefaultResponseKey;
        this.fxUserName = fxUserName;
        this.fxPassword = fxPassword;
        this.fxPort = fxPort;
        this.fxHost = fxHost;
        this.amqpClientService = amqpClientService;
        this.systemSettingRepository = systemSettingRepository;

    }

    @Override
    public Response<List<Cluster>> findAll(String org, Pageable pageable) {
        // Find all public or org.
        Page<com.fxlabs.fxt.dao.entity.clusters.Cluster> page = this.clusterRepository.findByVisibilityOrOrgId(ClusterVisibility.PUBLIC, org, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Cluster> findById(String id, String o) {
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = this.clusterRepository.findById(id);

        if (!clusterOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
        // validate user is entitled to use the cluster.
        if (!org.apache.commons.lang3.StringUtils.equals(clusterOptional.get().getOrg().getId(), o)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        return new Response<Cluster>(converter.convertToDto(clusterOptional.get()));
    }

    @Override
    public Response<Cluster> findByName(String id, String user) {
        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(id, "/")) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid region"));
        }
        String[] tokens = StringUtils.split(id, "/");
        String orgName = tokens[0];
        String clusterName = tokens[1];
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = this.clusterRepository.findByNameAndOrgName(clusterName, orgName);

        if (!clusterOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
        // TODO validate user is entitled to use the cluster.
        return new Response<Cluster>(converter.convertToDto(clusterOptional.get()));
    }


    @Override
    public Response<Cluster> create(Cluster dto, String org) {

        NameDto o = new NameDto();
        o.setId(org);
        dto.setOrg(o);


        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = clusterRepository.findByNameAndOrgId(dto.getName(), dto.getOrg().getId());
        if (clusterOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Duplicate cluster name"));
        }

        String queue = null;
        if (StringUtils.isEmpty(dto.getKey())) {
            queue = "key-" + RandomStringUtils.randomAlphabetic(12);
        } else {
            queue = dto.getKey();
        }
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        Queue q = new Queue(queue, true, false, false, args);
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
        amqpAdmin.declareQueue(q);
        amqpAdmin.declareBinding(binding);

        dto.setKey(queue);

        if (dto.getMin() == null) {
            dto.setMin(1);
        }

        com.fxlabs.fxt.dao.entity.clusters.Cluster cluster = this.clusterRepository.saveAndFlush(converter.convertToEntity(dto));
        this.clusterESRepository.save(cluster);


        if (dto.getAccount() != null && dto.getAccount().getAccountType().equals(AccountType.Self_Hosted)) {

            String script = getExecutionBotManualScript(queue);
            cluster.setManualScript(script);
            cluster.setRegion(AccountType.Self_Hosted.toString());

        } else {
            addExecBot(converter.convertToDto(cluster), cluster.getCreatedBy());
        }

        cluster = this.clusterRepository.saveAndFlush(cluster);
        this.clusterESRepository.save(cluster);

        return new Response<>(converter.convertToDto(cluster));
    }

    @Override
    public Response<Cluster> addExecBot(Cluster dto, String user) {

        //TODO validate - name not null and unique
        if (dto == null || org.apache.commons.lang3.StringUtils.isEmpty(dto.getId())) {
            throw new FxException("Invalid Cluster");
        }

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        // task.setSubscription(converter.convertToEntity(response.getData()));
        task.setType(TaskType.CREATE);
        task.setStatus(TaskStatus.PROCESSING);
        task.setClusterId(dto.getId());
        task = subscriptionTaskRepository.save(task);

        CloudTask cloudTask = new CloudTask();

        cloudTask.setId(task.getId());
        cloudTask.setType(CloudTaskType.CREATE);

        Map<String, String> opts = new HashMap<>();

        Account account = dto.getAccount();

        if (account == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Cloud account not found"));
        }
        String key = getCloudSkillKey(account);

        if (org.apache.commons.lang3.StringUtils.isEmpty(key)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No Skill found for the cloud"));
        }

        opts.put("ACCESS_KEY_ID", account.getAccessKey());
        opts.put("SECRET_KEY", getSecretKey(account.getId()));
        opts.put("COMMAND", getUserDataScript(dto.getKey()));
        opts.put("INSTANCE_NAME", dto.getName());
        opts.put("COUNT", dto.getMin().toString());
        opts.put("REGION", dto.getRegion());
        opts.put("KEY_PAIR", account.getProp1());
        opts.put("SUBNET", account.getProp3());
        opts.put("SECURITY_GROUP", account.getProp2());

        cloudTask.setOpts(opts);


        //send task to queue
        amqpClientService.sendTask(cloudTask, key);

        return new Response<Cluster>();
    }

    @Override
    public Response<Cluster> update(Cluster dto, String o, String user) {
        // validate user is entitled to use the cluster.
        if (!org.apache.commons.lang3.StringUtils.equals(dto.getId(), o)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        return super.save(dto, user);
    }

    @Override
    public Response<Cluster> delete(String clusterId, String o, String user) {
        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = repository.findById(clusterId);
        if (!clusterOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        // validate user is entitled to use the cluster.
        if (!org.apache.commons.lang3.StringUtils.equals(clusterOptional.get().getOrg().getId(), o)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        String queue = clusterOptional.get().getKey();
        amqpAdmin.deleteQueue(queue);
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
        amqpAdmin.removeBinding(binding);

        if (clusterOptional.get().getAccount() != null && !clusterOptional.get().getAccount().getAccountType().equals(com.fxlabs.fxt.dao.entity.clusters.AccountType.Self_Hosted)) {
            deleteExecBot(converter.convertToDto(clusterOptional.get()), clusterOptional.get().getCreatedBy());
        }

        return super.delete(clusterId, user);
    }


    @Override
    public Response<Long> countBotRegions(String user) {
        // Find all public
        Long count = this.clusterRepository.countByVisibility(ClusterVisibility.PUBLIC);
        return new Response<>(count);
    }

    @Override
    public void isUserEntitled(String s, String user) {
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = repository.findById(s);
        if (!clusterOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", s));
        }
        if (clusterOptional.get().getVisibility() == ClusterVisibility.PRIVATE && !org.apache.commons.lang3.StringUtils.equals(clusterOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s] with 'PRIVATE' visibility.", user, s));
        }
    }

    @Override
    public Response<Cluster> deleteExecBot(Cluster dto, String user) {

        dto.setStatus(ClusterStatus.DELETING);

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        // task.setSubscription(converter.convertToEntity(dto));
        task.setType(TaskType.DESTROY);
        task.setStatus(TaskStatus.PROCESSING);
        task.setClusterId(dto.getId());
        task = subscriptionTaskRepository.save(task);

        // TODO - send task to queue

        CloudTask cloudTask = new CloudTask();

        cloudTask.setId(task.getId());
        cloudTask.setType(CloudTaskType.DESTROY);

        Map<String, String> opts = new HashMap<>();

        Account account = dto.getAccount();
        String key = getCloudSkillKey(account);

        if (org.apache.commons.lang3.StringUtils.isEmpty(key)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No Skill found for the cloud"));
        }

        opts.put("ACCESS_KEY_ID", account.getAccessKey());
        opts.put("SECRET_KEY", getSecretKey(account.getId()));
        opts.put("REGION", dto.getRegion());

        if (org.apache.commons.lang3.StringUtils.isEmpty(dto.getNodeId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Node id is empty"));
        }

        opts.put("NODE_ID", dto.getNodeId());
        cloudTask.setOpts(opts);


        //send task to queue
        amqpClientService.sendTask(cloudTask, key);

        return new Response<Cluster>();
    }

    @Override
    public Response<String> pingExecBot(String id, String o) {

        PingTask task = new PingTask();

        task.setId(task.getId());

        Response<Cluster> clusterResponse = findById(id, o);

        if (clusterResponse.isErrors()) {
            return new Response<>().withMessages(clusterResponse.getMessages()).withErrors(true);
        }

        String key = clusterResponse.getData().getKey();

        if (org.apache.commons.lang3.StringUtils.isEmpty(key)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No Skill found for the cloud"));
        }

        //send task to queue
        String response = amqpClientService.sendTask(task, key);

        if (StringUtils.isEmpty(response)) {
            response = "Not reachable!";
        }

        return new Response<>(response);
    }

    private String getExecutionBotManualScript(String key) {
        StringBuilder sb = new StringBuilder();

        sb.append("docker").append(SPACE).append("run")
                .append(SPACE).append("-e")
                .append(SPACE).append("FX_KEY").append("=").append(key)
                .append(SPACE).append("fxlabs/bot");

        return sb.toString();
    }


    private String getUserDataScript(String key) {

        StringBuilder sb = new StringBuilder();

        ArrayList<String> lines = new ArrayList<String>();

        lines.add("#! /bin/bash");
        sb.append("sudo wget").append(SPACE)
                .append(fxExecutionBotScriptUrl).append(SPACE)
                .append("-O").append(SPACE)
                .append("fx_bot_install_script.sh");
        lines.add(sb.toString());

        String fxHost_ = null;

        Optional<SystemSetting> systemSettingOptional = this.systemSettingRepository.findByKey("fx.amqp.host");
        if (systemSettingOptional.isPresent()) {
            fxHost_ = systemSettingOptional.get().getValue();
        } else {
            fxHost_ = fxHost;
        }

        String port_ = null;

        Optional<SystemSetting> portSettingOptional = this.systemSettingRepository.findByKey("fx.amqp.port");
        if (portSettingOptional.isPresent()) {
            port_ = systemSettingOptional.get().getValue();
        } else {
            port_ = fxPort;
        }

        String fxUserName_ = null;

        Optional<SystemSetting> userNameSettingOptional = this.systemSettingRepository.findByKey("fx.amqp.username");
        if (userNameSettingOptional.isPresent()) {
            fxUserName_ = systemSettingOptional.get().getValue();
        } else {
            fxUserName_ = fxUserName;
        }

        String password_ = null;

        Optional<SystemSetting> passwordSettingOptional = this.systemSettingRepository.findByKey("fx.amqp.password");
        if (passwordSettingOptional.isPresent()) {
            password_ = systemSettingOptional.get().getValue();
        } else {
            password_ = fxPassword;
        }


        StringBuilder sb1 = new StringBuilder();
        sb1.append(" sudo bash fx_bot_install_script.sh").append(SPACE)
                .append(fxHost_).append(SPACE)
                .append(port_).append(SPACE)
                .append(fxUserName_).append(SPACE)
                .append(password_).append(SPACE)
                .append(key).append(SPACE)
                .append(fxDefaultResponseKey);
        lines.add(sb1.toString());

        String configScript = join(lines, "\n");

        logger.info("Bot configuaration script [{}]", configScript.toString());
        String str = new String(Base64.encodeBase64(configScript.getBytes()));

        return str;
    }


    private String join(Collection<String> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }

    private String getCloudSkillKey(Account account) {
        String key = null;
        if (account == null || account.getAccountType() == null) {
            return key;
        }
        switch (account.getAccountType()) {
            case AWS:
                key = "fx-caas-aws-ec2";
                break;
            default:
                logger.info("Provider [{}] not supported", account.getAccountType());
                break;
        }
        return key;
    }

    private String getSecretKey(String id) {
        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> accountOptional = accountRepository.findById(id);
        com.fxlabs.fxt.dao.entity.clusters.Account account = accountOptional.isPresent() ? accountOptional.get() : null;
        return account.getSecretKey();

    }

}
