package com.fxlabs.fxt.services.it;

import com.fxlabs.fxt.converters.project.JobIssueTrackerConverter;
import com.fxlabs.fxt.converters.skills.IssueTrackerConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.skills.TaskResult;
import com.fxlabs.fxt.dao.entity.skills.TaskStatus;
import com.fxlabs.fxt.dao.entity.skills.TaskType;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.*;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.it.IssueTrackerSaving;
import com.fxlabs.fxt.dto.it.State;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.project.JobIssueTracker;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.clusters.AccountService;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.run.RunService;
import com.fxlabs.fxt.services.users.SystemSettingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    private AccountService accountService;
    private SystemSettingService systemSettingService;
    private JobService jobService;
    private RunService runService;
    private JobIssueTrackerRepository jobIssueTrackerRepository;
    private JobIssueTrackerConverter jobIssueTrackerConverter;

    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public IssueTrackerServiceImpl(IssueTrackerRepository repository, IssueTrackerConverter converter,
                                   UsersRepository usersRepository, OrgUsersRepository orgUsersRepository,
                                   AmqpClientService amqpClientService, SubscriptionTaskRepository subscriptionTaskRepository, RunService runService,
                                   ClusterRepository clusterRepository, AccountService accountService, SystemSettingService systemSettingService,
                                   JobService jobService, JobIssueTrackerRepository jobIssueTrackerRepository,
                                   JobIssueTrackerConverter jobIssueTrackerConverter) {
        super(repository, converter);

        this.repository = repository;
        this.converter = converter;
//        this.amqpClientService = amqpClientService;
        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;
        this.clusterRepository = clusterRepository;
        this.subscriptionTaskRepository = subscriptionTaskRepository;
        this.accountService = accountService;
        this.systemSettingService = systemSettingService;
        this.jobService = jobService;
        this.runService = runService;
        this.jobIssueTrackerRepository = jobIssueTrackerRepository;
        this.jobIssueTrackerConverter = jobIssueTrackerConverter;

//        this.systemSettingRepository = systemSettingRepository;
    }


    @Override
    public Response<List<IssueTracker>> findAll(String org, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.it.IssueTracker> entities = this.repository.findByOrgId(org, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
    }

    @Override
    public Response<List<IssueTracker>> findBySkillType(String skillType, String org, Pageable pageable) {
        // TODO - find by skill-type and visibility -> PUBLIC or OWNER or ORG_PUBLIC
        Page<com.fxlabs.fxt.dao.entity.it.IssueTracker> entities = this.repository.findByOrgIdAndInactive(org, false, pageable);
        return new Response<>(converter.convertToDtos(entities.getContent()), entities.getTotalElements(), entities.getTotalPages());
        // return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Operation not supported.")));
    }

    @Override
    public Response<IssueTracker> findById(String id, String org) {
        Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> issueTrackerOptional = this.repository.findByIdAndOrgId(id, org);
        return new Response<>(converter.convertToDto(issueTrackerOptional.get()));
    }

    @Override
    public Response<IssueTracker> save(IssueTracker dto, String org, String user) {

        NameDto o = new NameDto();
        o.setId(org);
        dto.setOrg(o);

        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Account"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Issue Tracker name is empty"));
        }

        if (dto.getAccount() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account is empty"));
        }


        return super.save(dto, user);
    }

    @Override
    public Response<IssueTracker> addITBot(IssueTracker dto, String o, String user) {


        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for Account"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Issue Tracker name is empty"));
        }

        if (dto.getAccount() == null || StringUtils.isBlank(dto.getAccount().getId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Account is empty"));
        }else{
//            try {
                Response<Account> accountResp = accountService.findById(dto.getAccount().getId(), o);
                if (accountResp == null || accountResp.isErrors() || accountResp.getData() == null){
                    return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid Account id '" + dto.getAccount().getId() + "'"));
                }
//            }catch (NoSuchElementException ex){
//                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid Account id '" + dto.getAccount().getId() + "'"));
//            }
        }

        if (StringUtils.isEmpty(dto.getProp1())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Issue Tracker URL is empty"));
        }


        NameDto org = new NameDto();
        org.setId(o);
        dto.setOrg(org);


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
    public Response<IssueTracker> deleteITBot(String id, String o, String user) {

        // check user is owner or org_admin
        Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> issueTracker = repository.findByIdAndOrgId(id, o);

        IssueTracker dto = converter.convertToDto(issueTracker.get());
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
    public Response<JobIssueTracker> findJobIsseTrackerById(String id) {
        if (StringUtils.isEmpty(id)) {
            return new Response<>().withErrors(true);
        }

        Optional<com.fxlabs.fxt.dao.entity.project.JobIssueTracker> optional = jobIssueTrackerRepository.findByIdAndInactive(id, false);

        if (!optional.isPresent()) {
            return new Response<>().withErrors(true);
        }

        return new Response<>(jobIssueTrackerConverter.convertToDto(optional.get()));

    }


        @Override
    public Response<IssueTracker> findByName(String name, String organisation) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>().withErrors(true);
        }

        if (name.contains("/")) {

            String[] tokens = name.split("/");
            String org = tokens[0];
            String key = tokens[1];

            Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> optional = this.repository.findByOrgNameAndNameAndInactive(org, key, false);

            if (!optional.isPresent()) {
                return new Response<>().withErrors(true);
            }

            return new Response<>(converter.convertToDto(optional.get()));
        } else {

            if (StringUtils.isEmpty(organisation)) {
                return new Response<>().withErrors(true);
            }

            Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> optional = this.repository.findByOrgNameAndNameAndInactive(organisation, name, false);

            if (!optional.isPresent()) {
                return new Response<>().withErrors(true);
            }

            return new Response<>(converter.convertToDto(optional.get()));
        }
    }

    @Override
    public Response<Long> count(String orgId) {
        Long count = this.repository.countByOrgIdAndInactive(orgId, false);
        return new Response<>(count);
    }

    @Override
    public Response<IssueTrackerSaving> getIssueTrackerSavings(String id, String org, String owner) {

        if (StringUtils.isEmpty(id)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        Optional<com.fxlabs.fxt.dao.entity.it.IssueTracker> issueTrackerResponse = repository.findById(id);

        if (!issueTrackerResponse.isPresent() || !StringUtils.equals(issueTrackerResponse.get().getOrg().getId(), org)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid id"));
        }

        Response<List<Job>> jobResponse = jobService.findJobsByIssueTracker(issueTrackerResponse.get().getOrg().getName() + "/" +issueTrackerResponse.get().getName());

        String hourlyBugValidation = systemSettingService.findByKey("HOURLY_BUG_VALIDATION");
        String hourlyBugRate = systemSettingService.findByKey("HOURLY_BUG_RATE");

        int hourlyBugValidationInt = Integer.parseInt(hourlyBugValidation);
        int hourlyBugRateInt = Integer.parseInt(hourlyBugRate);

        IssueTrackerSaving its = new IssueTrackerSaving();
        its.setItid(id);

        if(CollectionUtils.isEmpty(jobResponse.getData())){
            its.setTotalCostSaving(0L);
            its.setTotalHourSaving(0L);

            its.setHourlyBugRate(hourlyBugRateInt);
            its.setHourlyBugValidation(hourlyBugValidationInt);
            return new Response<>(its);
        }

        List<Job>  jobList = jobResponse.getData();
        Integer variations = 0;

        for (Job job : jobList) {

            Response<List<Run>> runResponse = runService.findByJobIdForSaving(job.getId(), owner, PageRequest.of(0, 100, DEFAULT_SORT));

            if (CollectionUtils.isEmpty(runResponse.getData())) {
                continue;
            }

            for (Run run : runResponse.getData()) {
                Integer validationsForRun = run.getValidations() == null ? 0 : run.getValidations();
                variations = variations + validationsForRun;
            }

        }
//        Total Hour Savings                   200hrs      (formula: Validated/hr_bug_validation)
//        Total Cost Savings                   $1100 x 10  (formula: Validated/hr_bug_validation x hr_bug_rate)


        int totalHourSaving = variations / hourlyBugValidationInt;
        int totalCostSaving = totalHourSaving * hourlyBugRateInt;

        its.setTotalHourSaving((long) totalHourSaving);
        its.setTotalCostSaving((long) totalCostSaving);

        its.setHourlyBugRate(hourlyBugRateInt);
        its.setHourlyBugValidation(hourlyBugValidationInt);
        its.setValidatedCount(variations);

        return new Response<>(its);
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
