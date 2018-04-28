package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.CloudAccountConverter;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.es.CloudAccountESRepository;
import com.fxlabs.fxt.dao.repository.jpa.CloudAccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.CloudAccount;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 *  @author Mohammed Shoukath Ali
 * @since 4/28/2018
 */
@Service
@Transactional
public class CloudAccountServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.clusters.CloudAccount, CloudAccount, String> implements CloudAccountService {

    private CloudAccountRepository cloudAccountRepository;
    private CloudAccountESRepository cloudAccountESRepository;
    private CloudAccountConverter cloudAccountConverter;
    private AmqpAdmin amqpAdmin;
    private TopicExchange topicExchange;
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    public CloudAccountServiceImpl(CloudAccountRepository cloudAccountRepository, CloudAccountESRepository cloudAccountESRepository,
                                   CloudAccountConverter cloudAccountConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange,
                                   OrgUsersRepository orgUsersRepository) {

        super(cloudAccountRepository, cloudAccountConverter);

        this.cloudAccountRepository = cloudAccountRepository;
        this.cloudAccountESRepository = cloudAccountESRepository;
        this.cloudAccountConverter = cloudAccountConverter;
        this.amqpAdmin = amqpAdmin;
        this.topicExchange = topicExchange;
        this.orgUsersRepository = orgUsersRepository;

    }

    @Override
    public Response<List<CloudAccount>> findAll(String user, Pageable pageable) {
        // Find all public
        Page<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> page = this.cloudAccountRepository.findByCreatedBy(user, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<CloudAccount> findById(String id, String user) {
        com.fxlabs.fxt.dao.entity.clusters.CloudAccount cloudAccount = this.cloudAccountRepository.findById(id).get();
        return new Response<>(converter.convertToDto(cloudAccount));
    }

    @Override
    public Response<List<CloudAccount>> findByAccountType(String accountType, String user) {
        if (!EnumUtils.isValidEnum(CloudAccountPage.class, accountType)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Not a valid filter.")));
        }
        List<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> cloudAccount = this.cloudAccountRepository.findByAccountTypeInAndCreatedBy(CloudAccountPage.valueOf(accountType).getAccountTypes(), user);
        return new Response<>(converter.convertToDtos(cloudAccount));
    }

    @Override
    public Response<CloudAccount> findByName(String id, String user) {
        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(id, "/")) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid region"));
        }
        String[] tokens = StringUtils.split(id, "/");
        String orgName = tokens[0];
        String cloudAccountName = tokens[1];
        Optional<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> cloudAccountOptional = this.cloudAccountRepository.findByNameAndOrgName(cloudAccountName, orgName);

        if (!cloudAccountOptional.isPresent() && cloudAccountOptional.get().getVisibility() != ClusterVisibility.PUBLIC) {
            return new Response<>().withErrors(true);
        }
        // TODO validate user is entitled to use the cloudAccount.
        return new Response<CloudAccount>(converter.convertToDto(cloudAccountOptional.get()));
    }


    @Override
    public Response<CloudAccount> create(CloudAccount dto, String user) {
        // check duplicate name
        if (dto.getOrg() == null || StringUtils.isEmpty(dto.getOrg().getId())) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            NameDto o = new NameDto();
            o.setId(set.iterator().next().getOrg().getId());
            //o.setVersion(set.iterator().next().getOrg().getVersion());
            dto.setOrg(o);

        } else {
            // check user had write access to Org
            Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(dto.getOrg().getId(), user, OrgUserStatus.ACTIVE);
            if (!orgUsersOptional.isPresent() || orgUsersOptional.get().getOrgRole() != OrgRole.ADMIN) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [WRITE] or [ADMIN] access to the Org [%s]", dto.getOrg().getId())));
            }
        }

        Optional<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> cloudAccountOptional = cloudAccountRepository.findByNameAndOrgId(dto.getName(), dto.getOrg().getId());
        if (cloudAccountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Duplicate cloudAccount name"));
        }

//        String queue = null;
//        if (StringUtils.isEmpty(dto.getKey())) {
//            queue = "key-" + RandomStringUtils.randomAlphabetic(12);
//        } else {
//            queue = dto.getKey();
//        }
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-message-ttl", 3600000);
//        Queue q = new Queue(queue, true, false, false, args);
//        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
//        amqpAdmin.declareQueue(q);
//        amqpAdmin.declareBinding(binding);
//
//        dto.setKey(queue);

        // generate key

        com.fxlabs.fxt.dao.entity.clusters.CloudAccount cloudAccount = this.cloudAccountRepository.saveAndFlush(converter.convertToEntity(dto));
        this.cloudAccountESRepository.save(cloudAccount);
        return new Response<>(converter.convertToDto(cloudAccount));
    }

    @Override
    public Response<CloudAccount> update(CloudAccount dto, String user) {
        // validate user is the org admin
        return super.save(dto, user);
    }

    @Override
    public Response<CloudAccount> delete(String cloudAccountId, String user) {
        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> cloudAccountOptional = repository.findById(cloudAccountId);
        if (!cloudAccountOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
//        String queue = cloudAccountOptional.get().getKey();
//        amqpAdmin.deleteQueue(queue);
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-message-ttl", 3600000);
//        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
//        amqpAdmin.removeBinding(binding);
        return super.delete(cloudAccountId, user);
    }


    @Override
    public Response<Long> countBotRegions(String user) {
        // Find all public
        Long count = this.cloudAccountRepository.countByVisibility(ClusterVisibility.PUBLIC);
        return new Response<>(count);
    }

    @Override
    public void isUserEntitled(String s, String user) {
        Optional<com.fxlabs.fxt.dao.entity.clusters.CloudAccount> cloudAccountOptional = repository.findById(s);
        if (!cloudAccountOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", s));
        }
        if (cloudAccountOptional.get().getVisibility() == ClusterVisibility.PRIVATE && !org.apache.commons.lang3.StringUtils.equals(cloudAccountOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s] with 'PRIVATE' visibility.", user, s));
        }
    }
}
