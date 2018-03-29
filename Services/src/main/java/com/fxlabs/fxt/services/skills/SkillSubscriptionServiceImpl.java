package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.converters.skills.SkillSubscriptionConverter;
import com.fxlabs.fxt.dao.entity.skills.SubscriptionTask;
import com.fxlabs.fxt.dao.entity.skills.TaskResult;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.*;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskType;
import com.fxlabs.fxt.dto.clusters.CloudAccount;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.clusters.ClusterCloud;
import com.fxlabs.fxt.dto.clusters.ClusterStatus;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.dto.skills.SubscriptionState;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class SkillSubscriptionServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.skills.SkillSubscription, com.fxlabs.fxt.dto.skills.SkillSubscription, String> implements SkillSubscriptionService {

    private SkillSubscriptionRepository repository;
    private SkillSubscriptionConverter converter;
    private UsersRepository usersRepository;
    private OrgUsersRepository orgUsersRepository;
    private SubscriptionTaskRepository subscriptionTaskRepository;
    private ClusterRepository clusterRepository;
    private AmqpClientService amqpClientService;

    @Autowired
    public SkillSubscriptionServiceImpl(SkillSubscriptionRepository repository, SkillSubscriptionConverter converter,
                                        UsersRepository usersRepository, OrgUsersRepository orgUsersRepository,
                                        AmqpClientService amqpClientService,SubscriptionTaskRepository subscriptionTaskRepository, ClusterRepository clusterRepository) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;
        this.amqpClientService = amqpClientService;
        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;
        this.clusterRepository = clusterRepository;
        this.subscriptionTaskRepository = subscriptionTaskRepository;

    }


    @Override
    public Response<List<SkillSubscription>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> entities = this.repository.findByCreatedBy(user, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<List<SkillSubscription>> findBySkillType(String skillType, String user, Pageable pageable) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Page<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> entities = this.repository.findBySkillSkillTypeAndInactiveAndCreatedBy(com.fxlabs.fxt.dao.entity.skills.SkillType.valueOf(skillType), false, user, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<SkillSubscription> save(SkillSubscription dto, String user) {

        if (dto.getOrg() == null) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            OrgUsers orgUsers = null;
            orgUsers = set.iterator().next();
            NameDto org = new NameDto();
            org.setId(orgUsers.getOrg().getId());
            dto.setOrg(org);

        }
        return super.save(dto, user);
    }

    @Override
    public Response<SkillSubscription> addITBot(SkillSubscription dto, String user) {

        if (dto.getOrg() == null) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            OrgUsers orgUsers = null;
            orgUsers = set.iterator().next();
            NameDto org = new NameDto();
            org.setId(orgUsers.getOrg().getId());
            dto.setOrg(org);

        }

        //TODO validate - name not null and unique
        dto.setState(SubscriptionState.ACTIVE);

        if (dto.getVisibility() == null)
            dto.setVisibility(Visibility.PRIVATE);


        Response<SkillSubscription> response = super.save(dto, user);

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        task.setResult(TaskResult.SUCCESS);
        task.setSubscription(converter.convertToEntity(response.getData()));
        task.setType(TaskType.CREATE);
        task.setStatus(TaskStatus.COMPLETED);
        subscriptionTaskRepository.save(task);

        return response;
    }

    @Override
    public Response<SkillSubscription> deleteITBot(String id, String user) {
        // TODO check user is owner or org_admin
        Response<SkillSubscription> response = findById(id, user);
        SkillSubscription dto = response.getData();
        if (!StringUtils.equals(dto.getCreatedBy(), user)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [DELETE] access to the resource.")));
        }
        dto.setState(SubscriptionState.INACTIVE);
        dto.setInactive(true);

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        task.setResult(TaskResult.SUCCESS);
        task.setSubscription(converter.convertToEntity(dto));
        task.setType(TaskType.DESTROY);
        task.setStatus(TaskStatus.COMPLETED);
        subscriptionTaskRepository.save(task);

        return super.save(dto, user);
    }

    @Override
    public Response<SkillSubscription> addExecBot(Cluster dto, String user) {

        //TODO validate - name not null and unique
        if (dto == null || StringUtils.isEmpty(dto.getId())) {
            throw new FxException("Invalid Cluster");
        }


        if (dto.getOrg() == null) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            OrgUsers orgUsers = null;
            orgUsers = set.iterator().next();
            NameDto org = new NameDto();
            org.setId(orgUsers.getOrg().getId());
            dto.setOrg(org);

        }

//        dto.setState(SubscriptionState.LAUNCHING);
//        if (dto.getVisibility() == null)
//            dto.setVisibility(Visibility.PRIVATE);
//        Response<SkillSubscription> response = super.save(dto, user);

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

        CloudAccount cloudAccount = dto.getCloudAccount();
        String key = getCloudSkillKey(cloudAccount);

        if (StringUtils.isEmpty(key)){
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No Skill found for the cloud"));
        }

        opts.put("ACCESS_KEY_ID" , cloudAccount.getAccessKey());
        opts.put("SECRET_KEY", cloudAccount.getSecretKey());
        cloudTask.setOpts(opts);


        //send task to queue
        amqpClientService.sendTask(cloudTask, key);

        return new Response<SkillSubscription>();
    }

    private String getCloudSkillKey(CloudAccount cloudAccount) {
        String key = null;
        switch (cloudAccount.getCloudType()){
            case AWS:
                key = "fx-caas-aws-ec2";
                break;
            case AZURE:
                key = "fx-caas-azure";
                break;
                default:
                    logger.info("Invalid provider [{}]", cloudAccount.getCloudType());
                break;
        }
        return key;
    }

    @Override
    public Response<SkillSubscription> deleteExecBot(Cluster dto, String user) {

        // TODO check user is owner or org_admin
       // Response<SkillSubscription> response = findById(id, user);
       // SkillSubscription dto = response.getData();
        if (!StringUtils.equals(dto.getCreatedBy(), user)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [DELETE] access to the resource.")));
        }

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

        CloudAccount cloudAccount = dto.getCloudAccount();
        String key = getCloudSkillKey(cloudAccount);

        if (StringUtils.isEmpty(key)){
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "No Skill found for the cloud"));
        }

        opts.put("ACCESS_KEY_ID" , cloudAccount.getAccessKey());
        opts.put("SECRET_KEY", cloudAccount.getSecretKey());

        if (StringUtils.isEmpty(dto.getNodeId())){
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Node id is empty"));
        }

        opts.put("NODE_ID", dto.getNodeId());
        cloudTask.setOpts(opts);


        //send task to queue
        amqpClientService.sendTask(cloudTask, key);

        return new Response<SkillSubscription>();
    }

    @Override
    public Response<SkillSubscription> findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true);
        }
        String[] tokens = name.split("/");
        String org = tokens[0];
        String key = tokens[1];

        Optional<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> optional = this.repository.findByOrgNameAndNameAndInactive(org, key, false);

        if (!optional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        return new Response<>(converter.convertToDto(optional.get()));

    }

    @Override
    public Response<Long> countBySkillType(String user, SkillType skillType) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Long count = this.repository.countBySkillSkillTypeAndCreatedBy(com.fxlabs.fxt.dao.entity.skills.SkillType.valueOf(skillType.name()), user);
        return new Response<>(count);
    }


    @Override
    public void isUserEntitled(String id, String user) {
        Optional<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(optional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }

    }


}
