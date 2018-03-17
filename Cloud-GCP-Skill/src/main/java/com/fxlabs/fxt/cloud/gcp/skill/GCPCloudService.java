package com.fxlabs.fxt.cloud.gcp.skill;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.logging.log4j.config.Log4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class GCPCloudService implements CloudService {

    final Logger logger = LoggerFactory.getLogger(getClass());
    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    /**
     * <p>
     *  This method does only one thing.
     *   1. Creates Vm's on GCP
     * </p>
     *
     * @param task
     *  <p>
     *      Contains public cloud system connection information.
     *      e.g.
     *     VM configuaration
     *  </p>
     *
     *
     * @return
     *  <p>
     *      CloudTaskResponse - Should only set these properties.
     *      1. success - true/false
     *      2. logs - execution or error logs.
     *  </p>
     */
    @Override
    public CloudTaskResponse create(final CloudTask task) {
        logger.info("In GCPCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();

        ComputeService client = init(task);
        Template template = client.templateBuilder().hardwareId("Instance Type TODO")
                .osVersionMatches("10.06").imageDescriptionMatches("ubuntu-images").osFamily(OsFamily.UBUNTU).build();

        template.getOptions().inboundPorts(22);  // TODO:
        try {
            // Private Key or Password??
            Set set =  client.createNodesInGroup("group", 1);
            if (!CollectionUtils.isEmpty(set)){
                Iterator itr = set.iterator();
                NodeMetadata nodeMetadata = (NodeMetadata) itr.next();
                response.setKeys(nodeMetadata.getId());
            }


        } catch (RunNodesException e) {
            e.printStackTrace();
        }


        try {


            return response;

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return response;

    }


    private ComputeService init(CloudTask task){
        String url = task.getOpts().get("URL");
        String userName = task.getOpts().get("USERNAME");
        String secretKey = task.getOpts().get("PASSWORD");
        String domain = task.getOpts().get("DOMAIN");
        String image = task.getOpts().get("IMAGE");
        String subscription = task.getOpts().get("SUBSCRIPTION");


        ComputeServiceContext context = ContextBuilder.newBuilder("fx-labs")
                .credentials(userName, secretKey)
                .modules(com.google.common.collect.ImmutableSet.<Module> of(new Log4JLoggingModule(),
                        new SshjSshClientModule()))
                .buildView(ComputeServiceContext.class);

        ComputeService client = context.getComputeService();

        return client;
    }




//    private static ComputeService initComputeService(String provider, String identity, String credential) {
//
//        // example of specific properties, in this case optimizing image list to
//        // only amazon supplied
//        Properties properties = new Properties();
//        properties.setProperty(PROPERTY_EC2_AMI_QUERY, "owner-id=137112412989;state=available;image-type=machine");
//        properties.setProperty(PROPERTY_EC2_CC_AMI_QUERY, "");
//        long scriptTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES);
//        properties.setProperty(TIMEOUT_SCRIPT_COMPLETE, scriptTimeout + "");
//
//        // set oauth endpoint property if set in system property
//        String oAuthEndpoint = System.getProperty(PROPERTY_OAUTH_ENDPOINT);
//        if (oAuthEndpoint != null) {
//            properties.setProperty(PROPERTY_OAUTH_ENDPOINT, oAuthEndpoint);
//        }
//
//        // example of injecting a ssh implementation
//        Iterable<Module> modules = ImmutableSet.<Module> of(
//                new SshjSshClientModule(),
//                new SLF4JLoggingModule(),
//                new EnterpriseConfigurationModule());
//
//        ContextBuilder builder = ContextBuilder.newBuilder(provider)
//                .credentials(identity, credential)
//                .modules(modules)
//                .overrides(properties);
//
//        System.out.printf(">> initializing %s%n", builder.getApiMetadata());
//
//        return builder.buildView(ComputeServiceContext.class).getComputeService();
//    }


    /**
     * <p>
     *  This method does only one thing.
     *   1. Destroys Vm's on GCP
     * </p>
     *
     * @param task
     *  <p>
     *      Contains public cloud system connection information.
     *      e.g.
     *     VM configuaration
     *  </p>
     *
     *
     * @return
     *  <p>
     *      CloudTaskResponse - Should only set these properties.
     *      1. success - true/false
     *      2. logs - execution or error logs.
     *  </p>
     */


    @Override
    public CloudTaskResponse destroy(final CloudTask task) {
        logger.info("In  GCPCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();

        try {

            ComputeService client = init(task);
            client.destroyNode("modeId"); //TODO:

            return response;

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return response;

    }

}
