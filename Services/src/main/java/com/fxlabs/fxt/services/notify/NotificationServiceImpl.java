package com.fxlabs.fxt.services.notify;

import com.fxlabs.fxt.converters.notify.NotificationConverter;
import com.fxlabs.fxt.dao.entity.notify.NotificationVisibility;
import com.fxlabs.fxt.dao.repository.es.NotificationESRepository;
import com.fxlabs.fxt.dao.repository.jpa.NotificationRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notify.Notification;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 * @since 3/20/2018
 * @since 4/28/2018
 */
@Service
@Transactional
public class NotificationServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.notify.Notification, Notification, String> implements NotificationService {

    private NotificationRepository notificationAccountRepository;
    private NotificationESRepository notificationAccountESRepository;
    private NotificationConverter notificationAccountConverter;
    private AmqpAdmin amqpAdmin;
    private TopicExchange topicExchange;
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationAccountRepository, NotificationESRepository notificationAccountESRepository,
                                   NotificationConverter notificationAccountConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange,
                                   OrgUsersRepository orgUsersRepository) {

        super(notificationAccountRepository, notificationAccountConverter);

        this.notificationAccountRepository = notificationAccountRepository;
        this.notificationAccountESRepository = notificationAccountESRepository;
        this.notificationAccountConverter = notificationAccountConverter;
        this.amqpAdmin = amqpAdmin;
        this.topicExchange = topicExchange;
        this.orgUsersRepository = orgUsersRepository;

    }

    @Override
    public Response<List<Notification>> findAll(String org, Pageable pageable) {
        // Find all public
        Page<com.fxlabs.fxt.dao.entity.notify.Notification> page = this.notificationAccountRepository.findByOrgId(org, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Notification> findById(String id, String org) {
        com.fxlabs.fxt.dao.entity.notify.Notification NotificationAccount = this.notificationAccountRepository.findByIdAndOrgId(id, org).get();
        return new Response<>(converter.convertToDto(NotificationAccount));
    }

    @Override
    public Response<Notification> findByName(String id, String user) {
        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(id, "/")) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid region"));
        }
        String[] tokens = StringUtils.split(id, "/");
        String orgName = tokens[0];
        String NotificationAccountName = tokens[1];
        Optional<com.fxlabs.fxt.dao.entity.notify.Notification> NotificationAccountOptional = this.notificationAccountRepository.findByNameAndOrgName(NotificationAccountName, orgName);

        if (!NotificationAccountOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
        // TODO validate user is entitled to use the NotificationAccount.
        return new Response<Notification>(converter.convertToDto(NotificationAccountOptional.get()));
    }


    @Override
    public Response<Notification> create(Notification dto, String org, String user) {
        // check duplicate name

        NameDto o = new NameDto();
        o.setId(org);
        dto.setOrg(o);

        Optional<com.fxlabs.fxt.dao.entity.notify.Notification> NotificationAccountOptional = notificationAccountRepository.findByNameAndOrgId(dto.getName(), dto.getOrg().getId());
        if (NotificationAccountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Duplicate NotificationAccount name"));
        }

        com.fxlabs.fxt.dao.entity.notify.Notification NotificationAccount = this.notificationAccountRepository.saveAndFlush(converter.convertToEntity(dto));
        this.notificationAccountESRepository.save(NotificationAccount);
        return new Response<>(converter.convertToDto(NotificationAccount));
    }

    @Override
    public Response<Notification> update(Notification dto, String org, String user) {
        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.notify.Notification> notificationOptional = notificationAccountRepository.findByIdAndOrgId(dto.getId(), org);
        if (!notificationOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access."));
        }
        return super.save(dto, user);
    }

    @Override
    public Response<Notification> delete(String notificationAccountId, String org, String user) {
        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.notify.Notification> notificationOptional = notificationAccountRepository.findByIdAndOrgId(notificationAccountId, org);
        if (!notificationOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access."));
        }
        return super.delete(notificationAccountId, user);
    }


//    @Override
//    public Response<Long> countBotRegions(String user) {
//        // Find all public
//        Long count = this.NotificationAccountRepository.countByVisibility(NotificationVisibility.PUBLIC);
//        return new Response<>(count);
//    }

    @Override
    public void isUserEntitled(String s, String user) {
        /*Optional<com.fxlabs.fxt.dao.entity.notify.Notification> notificationAccountOptional = repository.findById(s);
        if (!notificationAccountOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", s));
        }
        if (notificationAccountOptional.get().getVisibility() == NotificationVisibility.PRIVATE && !org.apache.commons.lang3.StringUtils.equals(notificationAccountOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s] with 'PRIVATE' visibility.", user, s));
        }*/
    }

    @Override
    public Response<Long> count(String user) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Long count = this.notificationAccountRepository.countByCreatedByAndInactive(user, false);
        return new Response<>(count);
    }
}