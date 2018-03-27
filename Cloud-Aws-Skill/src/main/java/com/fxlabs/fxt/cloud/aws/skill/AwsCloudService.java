package com.fxlabs.fxt.cloud.aws.skill;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.ec2.domain.InstanceType;
import org.jclouds.enterprise.config.EnterpriseConfigurationModule;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_SCRIPT_COMPLETE;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class AwsCloudService implements CloudService {


    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FXLABS_AWS_DEFAULT_INSTANCE_TYPE = InstanceType.T2_MICRO;
    private static final String AWS_PKEY = "fxkey";
    private static final String FXLABS_AWS_DEFAULT_IMAGE = "us-west-1/ami-09d2fb69";
    private static final String FXLABS_AWS_DEFAULT_SECURITY_GROUP = "fx-sg";
    private static final String FXLABS_AWS_DEFAULT_VPC = "fx-vpc";
    private static final String AWS_PRIVATE_KEY_PEM = "";
    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    /**
     * <p>
     *  This method does only one thing.
     *   1. Creates Vm's on AWS
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
        logger.info("In IT AwsCloud Service for task [{}]", task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);
        response.setId(task.getId());
        ComputeService awsService = null;
        try {

            taskLogger.set(new StringBuilder());

            if (CollectionUtils.isEmpty(task.getOpts())) {
                return response;
            }

            Map<String, String> opts = task.getOpts();

            // Args
            String accessKeyId = opts.get("ACCESS_KEY_ID");
            String secretKey = opts.get("SECRET_KEY");

            awsService = getAwsService(accessKeyId, secretKey);

            TemplateBuilder templateBuilder = awsService.templateBuilder();

            //Instance type/size
            String hardware = getInstanceType(task);
            taskLogger.get().append("setting hardware id " + hardware);
            templateBuilder.hardwareId(hardware);

            String image = getImage(task);
            taskLogger.get().append("Setting image id : " + image);

            templateBuilder.imageId(image);

            Template template = templateBuilder.build();

            TemplateOptions options = template.getOptions();

            String awsPrivateKeyName = getAwsPrivateKey(opts);
            taskLogger.get().append("Setting Keypair " + awsPrivateKeyName);
            options.as(AWSEC2TemplateOptions.class).keyPair(awsPrivateKeyName);


            String securityGroup = getSecurityGroup(opts);
            taskLogger.get().append("Setting Security Group " + securityGroup);
            options.as(AWSEC2TemplateOptions.class).securityGroupIds(securityGroup);

            String network = getNetwork(opts);
            taskLogger.get().append("Setting Network " + network);
            options.as(AWSEC2TemplateOptions.class).networks(network);


            String username = getAwsImageUsername(opts);
            String password = getImagePassword(opts);


            if (!skipBotInstallation(opts)) {
                taskLogger.get().append("Installing Bot.....");
                LoginCredentials login = null;

                if (!StringUtils.isEmpty(username)) {
                    login = LoginCredentials.builder().user(username).privateKey(password).build();
                } else {
                    login = LoginCredentials.builder().privateKey(password).build();
                }

                if (login != null) {
                    options.runAsRoot(true).runScript(getBotInstallationScript(opts)).overrideLoginCredentials(login);
                }
            }
            NodeMetadata virtualBot = getOnlyElement(awsService.createNodesInGroup("fxlabs", 1, template));
            response.setSuccess(true);
            response.setResponseId(virtualBot.getId());

            return response;
        } catch (RunNodesException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage());
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } finally {
            if (awsService != null) {
                awsService.getContext().close();
            }
        }

        return response;

    }

    private String getInstanceType(CloudTask task) {
        String hardware = task.getOpts().get("HARDWARE");
        if (StringUtils.isEmpty(hardware)) {
            hardware = FXLABS_AWS_DEFAULT_INSTANCE_TYPE;
        }
        return hardware;
    }

    private String getImage(CloudTask task) {
        //image
        String value = task.getOpts().get("IMAGE");
        if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)){
            value = FXLABS_AWS_DEFAULT_IMAGE;
        }
        return value;
    }


    @Override
    public CloudTaskResponse destroy(final CloudTask task) {
        logger.info("In IT AwsCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);

        ComputeService awsService = null;

        try {
            taskLogger.set(new StringBuilder());
            if (CollectionUtils.isEmpty(task.getOpts())) {
                return response;
            }

            Map<String, String> opts = task.getOpts();
            String nodeId = opts.get("node_id");

            String accessKeyId = opts.get("ACCESS_KEY_ID");
            String secretKey = opts.get("SECRET_KEY");
            logger.info("Deleting bot [{}]..." , nodeId);
            taskLogger.get().append("Deleting VM " + nodeId);
            awsService = getAwsService(accessKeyId, secretKey);
            response.setSuccess(true);
            return response;

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } finally {
            if (awsService != null){
                awsService.getContext().close();
            }
        }

        return response;

    }

    private static ComputeService getAwsService(String accessKeyId, String secretKey) {

        Properties properties = new Properties();

        long scriptTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES);
        properties.setProperty(TIMEOUT_SCRIPT_COMPLETE, scriptTimeout + "");


        Iterable<Module> modules = ImmutableSet.<Module>of(
                new SshjSshClientModule(),
                new SLF4JLoggingModule(),
                new EnterpriseConfigurationModule());

        ContextBuilder builder = ContextBuilder.newBuilder("aws-ec2")
                .credentials(accessKeyId, secretKey)
                .modules(modules)
                .overrides(properties);
        System.out.print(builder.getApiMetadata());
        ComputeService client = builder.buildView(ComputeServiceContext.class).getComputeService();

        return client;
    }

    /**
     *
     * @param map
     * @return botinstall flag
     */
    private Boolean skipBotInstallation(Map<String, String> map) {

        String value = map.get("SKIP_BOT_INSTALL");

        if (!StringUtils.isEmpty(value)
                && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "true")) {
            return false;
        }

        return false;
    }

    /**
     *
     * @param opts
     * @return imageusername
     */
    private String getAwsImageUsername(Map<String, String> opts) {
        String value = opts.get("IMAGE_USERNAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return bot installation
     */
    private String getBotInstallationScript(Map<String, String> opts) {
        String value = opts.get("SCRIPT");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return keypair
     */
    private String getAwsPrivateKey(Map<String, String> opts){
        String value = opts.get("KEY_PAIR");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return AWS_PKEY;
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return imagepassword
     */
    private String getImagePassword(Map<String, String> opts){

        String value = opts.get("IMAGE_PASSWORD");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return AWS_PRIVATE_KEY_PEM;
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return security group
     */
    private String getSecurityGroup(Map<String, String> opts){
        String value = opts.get("SECURITY_GROUP");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return FXLABS_AWS_DEFAULT_SECURITY_GROUP;
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return NETWORK
     */
    private String getNetwork(Map<String, String> opts){
        String value = opts.get("NETWORK");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return FXLABS_AWS_DEFAULT_VPC;
        }
        return value;
    }


}
