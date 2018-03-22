package com.fxlabs.fxt.cloud.aws.skill;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_SCRIPT_COMPLETE;
import static org.jclouds.ec2.domain.InstanceType.T2_MICRO;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class AwsCloudService implements CloudService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String AWS_PKEY = "";
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
        try {

            if (CollectionUtils.isEmpty(task.getOpts())) {
                // TODO Add appro msgs and send response
                return response;
            }

            Map<String, String> opts = task.getOpts();

            // Args
            String accessKeyId = opts.get("ACCESS_KEY_ID");
            String secretKey = opts.get("SECRET_KEY");


            String image = task.getOpts().get("IMAGE");
            String subscription = task.getOpts().get("SUBSCRIPTION");

            String hardware = task.getOpts().get("HARDWARE");
            String locationId = task.getOpts().get("LOCATION");


            ComputeService awsService = getAwsService(accessKeyId, secretKey);

            TemplateBuilder templateBuilder = awsService.templateBuilder();

            //Instance type/size
            if(StringUtils.isEmpty(hardware)){
                hardware = InstanceType.T2_MICRO.toString();
            }
            taskLogger.get().append("setting hardware id " + hardware);
            templateBuilder.hardwareId(hardware);

            //image
            if(StringUtils.isEmpty(image)){
                image = "us-west-1/ami-09d2fb69";
            }
            taskLogger.get().append("Setting image id : " + image);
            templateBuilder.imageId(image);

            Template template = templateBuilder.build();

            TemplateOptions options = template.getOptions();

            String username = getAwsImageUsername(opts);
            String password = getPassword(opts);

            if(StringUtils.isEmpty(password)){
                password = AWS_PKEY;
            }
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
            NodeMetadata virtualMachine = getOnlyElement(awsService.createNodesInGroup("fxlabs", 1, template));
            response.setSuccess(true);
            response.setId(virtualMachine.getId());

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


    @Override
    public CloudTaskResponse destroy(final CloudTask task) {
        logger.info("In IT AwsCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();

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

    private Boolean skipBotInstallation(Map<String, String> map) {

        String value = map.get("skip_bot_install");

        if (!StringUtils.isEmpty(value)
                && org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "true")) {
            return true;
        }

        return false;
    }

    private String getAwsImageUsername(Map<String, String> opts) {
        String value = opts.get("image_username");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }

    private String getPassword(Map<String, String> opts){
        String password = opts.get("vm_password");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(password, "null")) {
            return "";
        }
        if(StringUtils.isEmpty(password)){
            return password;
        }

        return password;
    }

    private String getBotInstallationScript(Map<String, String> opts) {
        String value = opts.get("SCRIPT");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")) {
            return "";
        }
        return value;
    }

}
