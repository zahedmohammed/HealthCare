package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.AccountConverter;
import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import com.fxlabs.fxt.dao.entity.it.IssueTracker;
import com.fxlabs.fxt.dao.entity.notify.Notification;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.repository.es.AccountESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
public class AccountServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.clusters.Account, Account, String> implements AccountService {

    private AccountRepository accountRepository;
    //private AccountESRepository accountESRepository;
    private AccountConverter accountConverter;
    private AmqpAdmin amqpAdmin;
    private TopicExchange topicExchange;
    private OrgUsersRepository orgUsersRepository;
    private final static String PASSWORD_MASKED = "PASSWORD-MASKED";
    private ProjectRepository projectRepository;
    private ClusterRepository clusterRepository;
    private NotificationRepository notificationRepository;
    private IssueTrackerRepository issueTrackerRepository;
    private TextEncryptor encryptor;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountESRepository accountESRepository, IssueTrackerRepository issueTrackerRepository,
                              AccountConverter accountConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange, NotificationRepository notificationRepository,
                              OrgUsersRepository orgUsersRepository, ProjectRepository projectRepository, ClusterRepository clusterRepository,
                              TextEncryptor encryptor) {

        super(accountRepository, accountConverter);

        this.accountRepository = accountRepository;
        //this.accountESRepository = accountESRepository;
        this.accountConverter = accountConverter;
        this.amqpAdmin = amqpAdmin;
        this.topicExchange = topicExchange;
        this.orgUsersRepository = orgUsersRepository;
        this.projectRepository = projectRepository;
        this.clusterRepository = clusterRepository;
        this.notificationRepository = notificationRepository;
        this.issueTrackerRepository = issueTrackerRepository;
        this.encryptor = encryptor;

    }

    @Override
    public Response<List<Account>> findAll(String org, Pageable pageable) {
        // Find all public
        Page<com.fxlabs.fxt.dao.entity.clusters.Account> page = this.accountRepository.findByOrgIdAndInactive(org, false, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Account> findById(String id, String org) {
        com.fxlabs.fxt.dao.entity.clusters.Account account = this.accountRepository.findByIdAndOrgId(id, org).get();
        Account dto = converter.convertToDto(account);
        dto.setSecretKey(PASSWORD_MASKED);
        return new Response<>(dto);
    }

    @Override
    public Response<List<Account>> findByAccountType(String accountType, String org) {
        if (!EnumUtils.isValidEnum(AccountPage.class, accountType)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Not a valid filter.")));
        }
        List<com.fxlabs.fxt.dao.entity.clusters.Account> accounts = this.accountRepository.findByAccountTypeInAndOrgIdAndInactive(AccountPage.valueOf(accountType).getAccountTypes(), org, false);
        return new Response<>(converter.convertToDtos(accounts));
    }

    @Override
    public Response<Account> findByName(String id, String o) {
        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(id, "/")) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid region"));
        }
        String[] tokens = StringUtils.split(id, "/");
        String orgName = tokens[0];
        String cloudAccountName = tokens[1];
        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> cloudAccountOptional = this.accountRepository.findByIdAndOrgId(cloudAccountName, o);

        if (!cloudAccountOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
        // TODO validate user is entitled to use the cloudAccount.
        return new Response<Account>(converter.convertToDto(cloudAccountOptional.get()));
    }


    @Override
    public Response<Account> create(Account dto, String org) {
        NameDto o = new NameDto();
        o.setId(org);
        dto.setOrg(o);

        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Account"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account name is empty"));
        }

        if (dto.getAccountType() == null || StringUtils.isEmpty(dto.getAccountType().toString())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account Type is empty"));
        }

        switch (dto.getAccountType()) {
            case GitHub:
            case Git:
            case GitLab:
            case BitBucket:
            case Microsoft_TFS_Git:
            case Microsoft_VSTS_Git:
            case Jira:
            case AWS:
                if (StringUtils.isEmpty(dto.getAccessKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Username/Access-Key is empty"));
                }

                if (StringUtils.isEmpty(dto.getSecretKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Password/Secret-Key is empty"));
                }
                break;
            case Slack:
                if (StringUtils.isEmpty(dto.getSecretKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Slack Token is empty"));
                }
                break;
            case Email:
                if (StringUtils.isEmpty(dto.getAccessKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "From is empty"));
                }
                break;
        }


        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> cloudAccountOptional = accountRepository.findByNameAndOrgIdAndInactive(dto.getName(), dto.getOrg().getId(), false);
        if (cloudAccountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Duplicate cloudAccount name"));
        }

        // encrypt pass
        dto.setSecretKey(encryptor.encrypt(dto.getSecretKey()));

        com.fxlabs.fxt.dao.entity.clusters.Account cloudAccount = this.accountRepository.saveAndFlush(converter.convertToEntity(dto));
        //this.accountESRepository.save(cloudAccount);
        return new Response<>(converter.convertToDto(cloudAccount));
    }

    @Override
    public Response<Account> update(Account dto, String orgId, String user) {

        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Account"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account name is empty"));
        }

        if (dto.getAccountType() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account Type is empty"));
        }

        switch (dto.getAccountType()) {
            case GitHub:
            case Git:
            case GitLab:
            case BitBucket:
            case Microsoft_TFS_Git:
            case Microsoft_VSTS_Git:
            case Jira:
            case AWS:
                if (dto != null && StringUtils.isEmpty(dto.getAccessKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Username/Access-Key is empty"));
                }

                if (dto != null && StringUtils.isEmpty(dto.getSecretKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Password/Secret-Key is empty"));
                }
                break;
            case Slack:
                if (dto != null && StringUtils.isEmpty(dto.getSecretKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Slack Token is empty"));
                }
                break;
            case Email:
                if (dto != null && StringUtils.isEmpty(dto.getAccessKey())) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "From is empty"));
                }
            default:
                logger.info("Invalid Account Type [{}]", dto.getAccountType());
                break;
        }
        // dto.org == orgId
        if (!org.apache.commons.lang3.StringUtils.equals(dto.getOrg().getId(), orgId)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Unauthorized access."));
        }

        if (org.apache.commons.lang3.StringUtils.equals(PASSWORD_MASKED, dto.getSecretKey())) {

            Optional<com.fxlabs.fxt.dao.entity.clusters.Account> response = this.accountRepository.findById(dto.getId());

            if (response.isPresent() && response.get() != null) {
                dto.setSecretKey(response.get().getSecretKey());
            }
        } else {
            // encrypt pass
            dto.setSecretKey(encryptor.encrypt(dto.getSecretKey()));
        }

        return super.save(dto, user);
    }

    @Override
    public Response<Account> delete(String cloudAccountId, String orgId, String user) {


        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.clusters.Account> cloudAccountOptional = repository.findById(cloudAccountId);
        if (!cloudAccountOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        // dto.org == orgId
        if (!org.apache.commons.lang3.StringUtils.equals(cloudAccountOptional.get().getOrg().getId(), orgId)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Unauthorized access."));
        }

        switch (cloudAccountOptional.get().getAccountType()) {
            case GitHub:
            case Git:
            case GitLab:
            case BitBucket:
            case Microsoft_TFS_Git:
            case Microsoft_VSTS_Git:
            case Jira:
                List<Project> projects = projectRepository.findByAccountIdAndInactive(cloudAccountId, false);
                if (!CollectionUtils.isEmpty(projects)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Cannot delete this account. This account is being used by an active Project"));
                }
                List<IssueTracker> issueTrackers = issueTrackerRepository.findByAccountIdAndInactive(cloudAccountId, false);
                if (!CollectionUtils.isEmpty(issueTrackers)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Cannot delete this account. This account is being used by an active Issue Tracker"));
                }
                break;
            case AWS:
            case Self_Hosted:
                List<Cluster> clusters = clusterRepository.findByAccountIdAndInactive(cloudAccountId, false);
                if (!CollectionUtils.isEmpty(clusters)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Cannot delete this account. This account is being used by an active Bot"));
                }
                break;
            case Slack:
            case Email:
                List<Notification> notifications = notificationRepository.findByAccountIdAndInactive(cloudAccountId, false);
                if (!CollectionUtils.isEmpty(notifications)) {
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Cannot delete this account. This account is being used by an active Notification"));
                }
                break;
            default:
                logger.info("Invalid Account Type [{}]", cloudAccountOptional.get().getAccountType());
                break;

        }

        com.fxlabs.fxt.dao.entity.clusters.Account account = cloudAccountOptional.get();
        account.setInactive(true);

        return save(converter.convertToDto(account));

    }


    @Override
    public void isUserEntitled(String s, String user) {
    }
}
