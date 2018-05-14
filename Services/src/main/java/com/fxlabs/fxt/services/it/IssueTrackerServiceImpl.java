package com.fxlabs.fxt.services.it;

import com.fxlabs.fxt.converters.skills.IssueTrackerConverter;
import com.fxlabs.fxt.dao.entity.skills.TaskResult;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.*;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.it.State;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
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
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class IssueTrackerServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.it.IssueTracker, IssueTracker, String> implements IssueTrackerService {

    private IssueTrackerRepository repository;
    private IssueTrackerConverter converter;
    private UsersRepository usersRepository;
    private OrgUsersRepository orgUsersRepository;
    private SubscriptionTaskRepository subscriptionTaskRepository;
    private ClusterRepository clusterRepository;



    @Autowired
    public IssueTrackerServiceImpl(IssueTrackerRepository repository, IssueTrackerConverter converter,
                                   UsersRepository usersRepository, OrgUsersRepository orgUsersRepository,
                                   AmqpClientService amqpClientService, SubscriptionTaskRepository subscriptionTaskRepository,
                                   ClusterRepository clusterRepository ) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;
//        this.amqpClientService = amqpClientService;
        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;
        this.clusterRepository = clusterRepository;
        this.subscriptionTaskRepository = subscriptionTaskRepository;
//        this.systemSettingRepository = systemSettingRepository;
    }


    @Override
    public Response<List<IssueTracker>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.it.IssueTracker> entities = this.repository.findByCreatedBy(user, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<List<IssueTracker>> findBySkillType(String skillType, String user, Pageable pageable) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Page<com.fxlabs.fxt.dao.entity.it.IssueTracker> entities = this.repository.findByCreatedByAndInactive(user, false, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
        // return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Operation not supported.")));
    }

    @Override
    public Response<IssueTracker> save(IssueTracker dto, String user) {

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
    public Response<IssueTracker> addITBot(IssueTracker dto, String user) {

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
        dto.setState(State.ACTIVE);

        if (dto.getVisibility() == null)
            dto.setVisibility(Visibility.PRIVATE);


        Response<IssueTracker> response = super.save(dto, user);

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
    public Response<IssueTracker> deleteITBot(String id, String user) {
        // TODO check user is owner or org_admin
        Response<IssueTracker> response = findById(id, user);
        IssueTracker dto = response.getData();
        if (!StringUtils.equals(dto.getCreatedBy(), user)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [DELETE] access to the resource.")));
        }
        dto.setState(State.INACTIVE);
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
    public Response<IssueTracker> findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true);
        }
        String[] tokens = name.split("/");
        String org = tokens[0];
        String key = tokens[1];

        Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> optional = this.repository.findByOrgNameAndNameAndInactive(org, key, false);

        if (!optional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        return new Response<>(converter.convertToDto(optional.get()));

    }

    @Override
    public Response<Long> count(String user) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Long count = this.repository.countByCreatedByAndInactive(user, false);
        return new Response<>(count);
    }


    @Override
    public void isUserEntitled(String id, String user) {
        Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(optional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }

    }

}
