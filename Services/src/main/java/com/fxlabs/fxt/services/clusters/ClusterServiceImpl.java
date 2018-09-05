package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.ClusterConverter;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
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
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.users.SystemSettingService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.text.TextEncryptor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
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
    private SystemSettingRepository systemSettingRepository;
    private static String SPACE = " ";
    private TextEncryptor encryptor;
    private String fxCaasAwsEc2Queue;
    private SystemSettingService systemSettingService;
    private LocalEventPublisher localEventPublisher;

    @Autowired
    public ClusterServiceImpl(ClusterRepository clusterRepository, ClusterESRepository clusterESRepository, AccountRepository accountRepository,
                              ClusterConverter clusterConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange,
                              OrgUsersRepository orgUsersRepository, @Value("${fx.execution.bot.install.script.url}") String fxExecutionBotScriptUrl,
                              UsersRepository usersRepository, @Value("${fx.caas.aws_ec2.queue}") String fxCaasAwsEc2Queue,
                              AmqpClientService amqpClientService, SubscriptionTaskRepository subscriptionTaskRepository, LocalEventPublisher localEventPublisher,
                              SystemSettingRepository systemSettingRepository, TextEncryptor encryptor, SystemSettingService systemSettingService) {

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
        this.amqpClientService = amqpClientService;
        this.systemSettingRepository = systemSettingRepository;
        this.encryptor = encryptor;
        this.systemSettingService = systemSettingService;
        this.fxCaasAwsEc2Queue = fxCaasAwsEc2Queue;
        this.localEventPublisher = localEventPublisher;
    }

    @Override
    public Response<List<Cluster>> findAll(String org, Pageable pageable) {
        // Find all public or org.
        Page<com.fxlabs.fxt.dao.entity.clusters.Cluster> page = this.clusterRepository.findByOrgIdIn(Collections.singleton(org), pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<Cluster>> findEntitled(Collection<String> orgs, Pageable pageable) {
        // Find all public or org.
        Page<com.fxlabs.fxt.dao.entity.clusters.Cluster> page = this.clusterRepository.findByOrgIdIn(orgs, pageable);
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
    public Response<Cluster> findByName(String name, String user) {

        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(name, "/")) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid region"));
        }
        String[] tokens = StringUtils.split(name, "/");
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

        String encryptedQueue = this.encryptor.encrypt(queue);
        dto.setKey(encryptedQueue);

        if (dto.getMin() == null) {
            dto.setMin(1);
        }

        com.fxlabs.fxt.dao.entity.clusters.Cluster cluster = this.clusterRepository.saveAndFlush(converter.convertToEntity(dto));
        this.clusterESRepository.save(cluster);


        if (dto.getAccount() != null && dto.getAccount().getAccountType().equals(AccountType.Self_Hosted)) {

            String host = systemSettingService.findByKey(SystemSettingService.FX_HOST);
            String port = systemSettingService.findByKey(SystemSettingService.FX_PORT);
            String ssl = systemSettingService.findByKey(SystemSettingService.FX_SSL);
            String iam = systemSettingService.findByKey(SystemSettingService.FX_IAM);
            String tag = systemSettingService.findByKey(SystemSettingService.BOT_TAG);

            String script = getExecutionBotManualScript(host, port, ssl, iam, encryptedQueue, tag);
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
        try {
            botExecEvent(dto, Status.In_progress, Entity.Bot, task.getId(), null, Type.Deploy);
        } catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }

        return new Response<Cluster>();
    }


    public void botExecEvent(Cluster dto, Status status, Entity entityType, String taskId, String logId, Type type) {

        if (dto == null || status == null || entityType == null) {

            logger.info("Invalid event for project sync" );
            return;
        }


        Event event = new Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(dto.getName());
        event.setUser(dto.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(type);
        event.setEntityId(dto.getId());
        event.setLink("/app/regions/" + dto.getId());

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(dto.getOrg().getName());
        org.setId(dto.getOrg().getId());
        event.setOrg(org);
        event.setLogId(logId);


        logger.info("Creating bot [{}] and status [{}] for task type [{}]" , dto.getId(), status.toString(), event.getName());
        localEventPublisher.publish(event);
    }

    @Override
    public Response<Cluster> update(Cluster dto, String o, String user) {
        // validate user is entitled to use the cluster.
        if (!org.apache.commons.lang3.StringUtils.equals(dto.getOrg().getId(), o)) {
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
            Response<Cluster> clusterResponse = deleteExecBot(converter.convertToDto(clusterOptional.get()), clusterOptional.get().getCreatedBy());

            if(clusterResponse.isErrors()) {
                return super.delete(clusterId, user);
            }

            clusterOptional.get().setInactive(true);
            return save(converter.convertToDto(clusterOptional.get()));
        }

        return super.delete(clusterId, user);

    }


    @Override
    public Response<Long> countBotRegions(String orgId) {
        // Find all public
        Long count = this.clusterRepository.countByOrgId(orgId);
        return new Response<>(count);
    }

    @Override
    public void isUserEntitled(String s, String user) {
        /*Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = repository.findById(s);
        if (!clusterOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", s));
        }
        if (clusterOptional.get().getVisibility() == ClusterVisibility.PRIVATE && !org.apache.commons.lang3.StringUtils.equals(clusterOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s] with 'PRIVATE' visibility.", user, s));
        }*/
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
        try {
            botExecEvent(dto, Status.In_progress, Entity.Bot, task.getId(), null, Type.Delete);
        } catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }

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
        key = encryptor.decrypt(key);

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

    @Override
    public Response<Saving> savings(String id, String o) {

        if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterResponse = clusterRepository.findById(id);

        if (!clusterResponse.isPresent() || !org.apache.commons.lang3.StringUtils.equals(clusterResponse.get().getOrg().getId(), o)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        com.fxlabs.fxt.dao.entity.clusters.Cluster entity = clusterResponse.get();

        String licenseSetting = systemSettingService.findByKey("LICENSE_SAVING");
        String managedInstanceSetting = systemSettingService.findByKey("MANAGED_INSTANCE_SAVING");

        Saving saving = new Saving();
        saving.setStartDate(entity.getCreatedDate());

        DateTime dt = new DateTime(entity.getCreatedDate());
        LocalDate dateToReturn = new LocalDate(entity.getCreatedDate());
        int calMonths = monthsBetweenIgnoreDays(new LocalDate(entity.getCreatedDate()), LocalDate.now());

        saving.setCalMonths(calMonths);
        saving.setCount(entity.getMin());

        int licenseSaving = calMonths * entity.getMin() * Integer.parseInt(licenseSetting);
        int managedInstanceSaving = calMonths * entity.getMin() * Integer.parseInt(licenseSetting);

        if (entity.getAccount().getAccountType() == com.fxlabs.fxt.dao.entity.clusters.AccountType.Self_Hosted) {
            managedInstanceSaving = 0;
        }

        saving.setLicenseSaving(licenseSaving);
        saving.setManagedInstanceSaving(managedInstanceSaving);

        saving.setTotal(licenseSaving + managedInstanceSaving);

        return new Response<Saving>(saving);
    }

    private int monthsBetweenIgnoreDays(LocalDate start, LocalDate end) {
        start = start.withDayOfMonth(1);
        end = end.withDayOfMonth(1);
        int mos = Months.monthsBetween(start, end).getMonths();
        return mos == 0 ? 1 : mos;
    }

    private String getExecutionBotManualScript(String host, String port, String ssl, String iam, String key, String tag) {
        StringBuilder sb = new StringBuilder();

        sb.append("docker").append(SPACE).append("run")
                .append(SPACE).append("-d")
//                .append(SPACE).append("-e").append(SPACE).append("FX_HOST").append("=").append(host)
//                .append(SPACE).append("-e").append(SPACE).append("FX_PORT").append("=").append(port)
//                .append(SPACE).append("-e").append(SPACE).append("FX_SSL").append("=").append(ssl)
                .append(SPACE).append("-e").append(SPACE).append("FX_IAM").append("=").append(iam)
                .append(SPACE).append("-e").append(SPACE).append("FX_KEY").append("=").append(key)
                .append(SPACE).append("fxlabs/bot:").append(tag);

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

        String host = systemSettingService.findByKey(SystemSettingService.FX_HOST);
        String port = systemSettingService.findByKey(SystemSettingService.FX_PORT);
        String ssl = systemSettingService.findByKey(SystemSettingService.FX_SSL);
        String iam = systemSettingService.findByKey(SystemSettingService.FX_IAM);
        String tag = systemSettingService.findByKey(SystemSettingService.BOT_TAG);


        StringBuilder sb1 = new StringBuilder();
        sb1.append(" sudo bash fx_bot_install_script.sh").append(SPACE)
                .append(host).append(SPACE)
                .append(port).append(SPACE)
                .append(ssl).append(SPACE)
                .append(iam).append(SPACE)
                .append(key).append(SPACE)
                .append(tag);
        lines.add(sb1.toString());

        String configScript = join(lines, "\n");

        logger.info("Bot configuration script [{}]", configScript.toString());
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
                key = fxCaasAwsEc2Queue;
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
        return encryptor.decrypt(account.getSecretKey());

    }

}
