package com.fxlabs.fxt.services.skills;

import com.fxlabs.fxt.converters.skills.SkillSubscriptionConverter;
import com.fxlabs.fxt.dao.entity.skills.TaskResult;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.SkillSubscriptionRepository;
import com.fxlabs.fxt.dao.repository.jpa.SubscriptionTaskRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.*;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.dto.skills.SubscriptionState;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    public SkillSubscriptionServiceImpl(SkillSubscriptionRepository repository, SkillSubscriptionConverter converter,
                                        UsersRepository usersRepository, OrgUsersRepository orgUsersRepository,
                                        SubscriptionTaskRepository subscriptionTaskRepository) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;

        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;

        this.subscriptionTaskRepository = subscriptionTaskRepository;

    }


    @Override
    public Response<List<SkillSubscription>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> entities = this.repository.findByCreatedBy(user, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<List<SkillSubscription>> findBySkillType(String user, String skillType, Pageable pageable) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Page<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> entities = this.repository.findBySkillSkillTypeAndCreatedBy(com.fxlabs.fxt.dao.entity.skills.SkillType.valueOf(skillType), user, pageable);
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
    public Response<SkillSubscription> addExecBot(SkillSubscription dto, String user) {

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
        dto.setState(SubscriptionState.LAUNCHING);

        if (dto.getVisibility() == null)
            dto.setVisibility(Visibility.PRIVATE);


        Response<SkillSubscription> response = super.save(dto, user);

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        task.setSubscription(converter.convertToEntity(response.getData()));
        task.setType(TaskType.CREATE);
        task.setStatus(TaskStatus.PROCESSING);
        subscriptionTaskRepository.save(task);

        // TODO - send task to queue

        return response;
    }

    @Override
    public Response<SkillSubscription> deleteExecBot(String id, String user) {

        // TODO check user is owner or org_admin
        Response<SkillSubscription> response = findById(id, user);
        SkillSubscription dto = response.getData();
        if (!StringUtils.equals(dto.getCreatedBy(), user)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [DELETE] access to the resource.")));
        }

        dto.setState(SubscriptionState.DELETING);

        // Add Task
        com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = new com.fxlabs.fxt.dao.entity.skills.SubscriptionTask();
        task.setSubscription(converter.convertToEntity(dto));
        task.setType(TaskType.DESTROY);
        task.setStatus(TaskStatus.PROCESSING);
        subscriptionTaskRepository.save(task);

        // TODO - send task to queue

        return super.save(dto, user);
    }

    @Override
    public Response<SkillSubscription> findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true);
        }
        String[] tokens = name.split("/");
        String org = tokens[0];
        String key = tokens[1];

        Optional<com.fxlabs.fxt.dao.entity.skills.SkillSubscription> optional = this.repository.findByOrgNameAndName(org, key);

        if (!optional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        return new Response<>(converter.convertToDto(optional.get()));

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