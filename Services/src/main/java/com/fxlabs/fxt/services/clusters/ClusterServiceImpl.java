package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.ClusterConverter;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.es.ClusterESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ClusterRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    private AmqpAdmin amqpAdmin;
    private TopicExchange topicExchange;
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    public ClusterServiceImpl(ClusterRepository clusterRepository, ClusterESRepository clusterESRepository,
                              ClusterConverter clusterConverter, AmqpAdmin amqpAdmin, TopicExchange topicExchange,
                              OrgUsersRepository orgUsersRepository) {

        super(clusterRepository, clusterConverter);

        this.clusterRepository = clusterRepository;
        this.clusterESRepository = clusterESRepository;
        this.clusterConverter = clusterConverter;
        this.amqpAdmin = amqpAdmin;
        this.topicExchange = topicExchange;
        this.orgUsersRepository = orgUsersRepository;

    }

    @Override
    public Response<List<Cluster>> findAll(String user) {
        // Find all public
        List<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusters = clusterESRepository.findByVisibility(ClusterVisibility.PUBLIC);
        return new Response<>(converter.convertToDtos(clusters));
    }

    @Override
    public Response<Cluster> findById(String id, String user) {
        return null;
    }

    @Override
    public Response<Cluster> findByName(String id, String user) {
        // org//name
        if (!org.apache.commons.lang3.StringUtils.contains(id, "//")) {
            return new Response<>().withErrors(true);
        }
        String[] tokens = StringUtils.split(id, "//");
        String orgName = tokens[0];
        String clusterName = tokens[1];
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = this.clusterRepository.findByNameAndOrgName(clusterName, orgName);

        if (!clusterOptional.isPresent()) {
            return  new Response<>().withErrors(true);
        }
        // TODO validate user is entitled to use the cluster.
        return new Response<Cluster>(converter.convertToDto(clusterOptional.get()));
    }


    @Override
    public Response<Cluster> create(Cluster dto, String user) {
        // check duplicate name
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = clusterRepository.findByNameAndOrgId(dto.getName(), dto.getOrg().getId());
        if (clusterOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Duplicate cluster name"));
        }

        if (StringUtils.isEmpty(dto.getOrg().getId())) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            NameDto o = new NameDto();
            o.setId(set.iterator().next().getOrg().getId());
            o.setVersion(set.iterator().next().getOrg().getVersion());
            dto.setOrg(o);

        } else {
            // check user had write access to Org
            Optional<com.fxlabs.fxt.dao.entity.users.OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(dto.getOrg().getId(), user, OrgUserStatus.ACTIVE);
            if (!orgUsersOptional.isPresent() || orgUsersOptional.get().getOrgRole() != OrgRole.ADMIN) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [WRITE] or [ADMIN] access to the Org [%s]", dto.getOrg().getId())));
            }
        }

        String queue = "key-" + RandomStringUtils.randomAlphabetic(12);
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        Queue q =  new Queue(queue, true, false, false, args);
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
        amqpAdmin.declareQueue(q);
        amqpAdmin.declareBinding(binding);

        dto.setKey(queue);

        // generate key

        return super.save(dto);
    }

    @Override
    public Response<Cluster> update(Cluster dto, String user) {
        // validate user is the org admin
        return super.save(dto);
    }

    @Override
    public Response<Cluster> delete(String clusterId, String user) {
        // validate user is the org admin
        Optional<com.fxlabs.fxt.dao.entity.clusters.Cluster> clusterOptional = repository.findById(clusterId);
        if (!clusterOptional.isPresent()) {
            return new Response<>().withErrors(true);
        }
        String queue = clusterOptional.get().getKey();
        amqpAdmin.deleteQueue(queue);
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 3600000);
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE, topicExchange.getName(), queue, args);
        amqpAdmin.removeBinding(binding);
        return super.delete(clusterId);
    }
}
