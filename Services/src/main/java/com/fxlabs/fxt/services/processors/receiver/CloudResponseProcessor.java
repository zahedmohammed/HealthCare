package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.entity.clusters.ClusterStatus;
import com.fxlabs.fxt.dao.entity.skills.*;
import com.fxlabs.fxt.dao.repository.jpa.ClusterRepository;
import com.fxlabs.fxt.dao.repository.jpa.IssueTrackerRepository;
import com.fxlabs.fxt.dao.repository.jpa.SubscriptionTaskRepository;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class CloudResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private SubscriptionTaskRepository repository;
    private IssueTrackerRepository subscriptionRepository;
    private ClusterRepository clusterRepository;


    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");


    @Autowired
    public CloudResponseProcessor(SubscriptionTaskRepository repository, IssueTrackerRepository subscriptionRepository,
                                    ClusterRepository clusterRepository) {
        this.repository = repository;
        this.clusterRepository = clusterRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void process(CloudTaskResponse response) {

        logger.info("Received CloudTaskResponse id [{}]...", response.getId());
        try {

            // TODO - Response [task-id, ids, logs, success]
            // -> Update subscription
            // -> Update task Done
            // -> Generate Alert
            if (StringUtils.isEmpty(response.getId())){
                logger.warn("Subscription Task id for cloud-task response is {}", response.getId());
                return;
            }

            Optional<com.fxlabs.fxt.dao.entity.skills.SubscriptionTask> optional = repository.findById(response.getId());

            if(!optional.isPresent()) {
                logger.warn("Invalid cloud-task id {}", response.getId());
                return;
            }

            if (StringUtils.isEmpty(optional.get().getClusterId())) {
                logger.warn("Invalid cloud-task id {}", response.getId());
                return;
            }



            com.fxlabs.fxt.dao.entity.skills.SubscriptionTask task = optional.get();
            Optional<Cluster> clusterData = clusterRepository.findById(task.getClusterId());


            task.setStatus(TaskStatus.COMPLETED);
            task.setLogs(response.getLogs());
            task.setResult(TaskResult.FAILURE);
           // clusterData.get().setStatus(ClusterStatus.FAILED);

            if (clusterData.isPresent() && response.getSuccess() && task.getType() == TaskType.CREATE) {
                task.setResult(TaskResult.SUCCESS);
                clusterData.get().setStatus(ClusterStatus.ACTIVE);
                clusterData.get().setNodeId(response.getResponseId());
                clusterRepository.save(clusterData.get());
            }

            if (clusterData.isPresent() && response.getSuccess() && task.getType() == TaskType.DESTROY) {
                task.setResult(TaskResult.SUCCESS);
                clusterData.get().setStatus(ClusterStatus.DELETED);
                clusterData.get().setInactive(true);
                clusterRepository.save(clusterData.get());
            }
            repository.save(task);



            // TODO
            Alert alert = new Alert();



        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

}
