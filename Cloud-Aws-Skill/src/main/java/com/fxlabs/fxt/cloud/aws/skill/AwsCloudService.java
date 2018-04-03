package com.fxlabs.fxt.cloud.aws.skill;

import com.amazonaws.auth.*;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class AwsCloudService implements CloudService {


    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FXLABS_AWS_DEFAULT_INSTANCE_TYPE = InstanceType.T2Small.toString();
    private static final String AWS_PKEY = "fxlabs";
    //private static final String FXLABS_AWS_DEFAULT_IMAGE = "ami-09d2fb69";
    private static final String FXLABS_AWS_DEFAULT_IMAGE = "ami-925144f2";

    private static final String FXLABS_AWS_DEFAULT_SECURITY_GROUP = "fx-sg";
    private static final String FXLABS_AWS_DEFAULT_SECURITY_GROUP_ID = "sg-7e476807";
    private static final String FXLABS_AWS_DEFAULT_SUBNET_ID = "subnet-ef5b0e88";

    private static final String FXLABS_AWS_DEFAULT_REGION = "us-west-1";

    private static final String FXLABS_AWS_DEFAULT_VPC = "fx-vpc";
    private String AWS_PRIVATE_KEY_PEM = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAo4dmeW4TyJU8VLoxlg24j9rscx27cKL7/2kfVVf7rEwZ0P8i4e7ITz1Lwz4X\n" +
            "AzO6K7YftCH9XjDtFef0dx+k7hAfHvUuu/+snwINy8aUpB7BOQHjMNiEv1reRAXXtWpZMDMPCfAf\n" +
            "lG9gREQ7dEUgCbOrxBCn6jkAoGrSwKUCxOn+loXl6uI3lfPYtNyNS1N5nG4zXWrgQ9EOx7NjigDw\n" +
            "Zdj+YIGmFC18tg0Ka3u4bqyljCsrBtgVF4ryg4sCWHQbVtgdc/UmCVZmUH/IU0AOI0RSB6iQ5Dfe\n" +
            "kYoH02FqvcVepOl+w+e6RZF5kMMMHkcxCk5L8LL8bjAldYdW0TUcNwIDAQABAoIBAFrLG49CGcPd\n" +
            "cADO3Ccm+RkOAQgtMtvn8iguEpKM5hQUsKTEc3aj4B12t/DwMVC60JviY5nc4VMBsTDfd3EIZccz\n" +
            "tkO297qzywEB3+0LFY5F8RwYuy5smM+xwQRdhhWYtWo4fxoSc0YzzGw6whPdKVuZoT2bzc8S1RE8\n" +
            "HkWNmIsBSCPM2L9ItUreicXtRZpBmyu6O9EuArwAyWUa+lmjv2Ku1dJXbyq4ZCVZl7GCmLSfozXL\n" +
            "NhElVBI6JJlVPi6wYYbymQDxFscwgX3ADJlhNd4a6qm4jJLyop43O9a4jIPyYm1C0hMCwP4j6Zvh\n" +
            "2JUmRbstTA8iRSMdRwS1tqnaYtECgYEA3zS3FMX/xQXHHAU8LHGLSdz3ZrA+qBXLqCONQgWHr1mp\n" +
            "UdLALTGLw/V69B6jaIFCF+wkbgMRFWm9d2aWB7N1lUsCjOkgFdASaqZ8FDydRT87KxkGGAgbi7RV\n" +
            "uE6np0bWHV8njiZo5t5+FK5orOLS52xf+/7ODM4P2WvXAANje4kCgYEAu44YqFLEShWBXMEGX/7m\n" +
            "UtFCz36pkjoOHXdH9E2abuiraRbatkEy3yHxi1BE6tqqDLJoNXd5F+XuIkBshbce5j1yk2UZsH1u\n" +
            "KbsLJs5RzC5fyBUWzwitzm+w7DLr4CbbgsLf8247dgdLn5b1Jjd8vO6rfNXT0udp7CFZ3QXbKb8C\n" +
            "gYA8+xj92u569I4mcKO0/LxyTKVm78XehD3kzPm9zOb6GEPzL+IDNuMZgYq7AVfhqFbXVFPLnpjf\n" +
            "QclawrNAnV4FkL28o8B5VSSC8MM7gCfzkEfpCWgpzqU/8N/uf8a8I9VBpwqWgpXsZWMK4W+FtF6s\n" +
            "jWZX4ZCH3RBldVoDDenzCQKBgG6e6tAssPV5JamNd7Ma5sImBp/XdzB16WlbtybStql/tcnv3uPs\n" +
            "JJMStCKVH3Dds7p1Z51RcCy2QvQUx20+io0F5RQmZzZ/ZIBcf8FNo1UobPnX5nIKDmlZ2yIVDqZQ\n" +
            "hNvlEK3FcHC28NWZ9dGqnHna0253t84HC6RoL1Z7Y76FAoGAJdMktiD/QoBAwQA/CnwAEpIT3kGg\n" +
            "ebyX+nt7yRci1d3rZdz+bx5O4tYyWoVVXo7I1J697wqp8PRSXgStSqOywzclhWbdyENmdNOidloJ\n" +
            "KbIDQyzPNrmomu15fdqnqdEHA7ovvI2yUKPlmwt/kToEUCTfpoMOcuwPFQkXvBO8uCQ=\n" +
            "-----END RSA PRIVATE KEY-----";
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
        AmazonEC2  awsService = null;
        try {

            taskLogger.set(new StringBuilder());

            if (task == null || CollectionUtils.isEmpty(task.getOpts())) {
                taskLogger.get().append("Options empty for takd id :" +  task.getId());
                logger.info("Options empty for takd id : [{}]", task.getId());
                return response;
            }

            Map<String, String> opts = task.getOpts();

            // Args
            String accessKeyId = opts.get("ACCESS_KEY_ID");
            String secretKey = opts.get("SECRET_KEY");

            String region = getRegion(opts);

            awsService = getAwsEc2Service(accessKeyId, secretKey, region);

            //Instance type/size
            String hardware = getInstanceType(task);
            taskLogger.get().append("setting hardware id " + hardware);

            String image = getImage(task);
            taskLogger.get().append("Setting image id : " + image);

            String awsPrivateKeyName = getAwsPrivateKey(opts);
            taskLogger.get().append("Setting Keypair " + awsPrivateKeyName);

            String securityGroup = getSecurityGroup(opts);
            taskLogger.get().append("Setting Security Group " + securityGroup);

            String network = getNetwork(opts);
            taskLogger.get().append("Setting Network " + network);

            String username = getAwsImageUsername(opts);
            String password = getImagePassword(opts);
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withImageId(image)
                    .withInstanceType(com.amazonaws.services.ec2.model.InstanceType.T2Small)
                    .withMinCount(1).withMaxCount(1)
                    .withKeyName(awsPrivateKeyName)
                    .withSubnetId(getSubnet(opts))
                    .withSecurityGroupIds(securityGroup);

            //getECSuserData(runInstancesRequest, opts);
            runInstancesRequest.withUserData(getBotConfigScript(opts));

            RunInstancesResult run_response = awsService.runInstances(runInstancesRequest);

            String instance_id = run_response.getReservation().getInstances().get(0).getInstanceId();

            String tag = getInstanceTag(opts);
            if (!StringUtils.isEmpty(tag)) {
                CreateTagsRequest createTagsRequest = new CreateTagsRequest();
                createTagsRequest.withResources(run_response.getReservation().getInstances().get(0).getInstanceId()) //
                        .withTags(new Tag("Name", tag));
                awsService.createTags(createTagsRequest);
            }

            response.setSuccess(true);
            response.setResponseId(instance_id);
            response.setLogs(taskLogger.get().toString());
            return response;
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } finally {
            //TODO close any connections if
        }

        return response;

    }

    @Override
    public CloudTaskResponse destroy(final CloudTask task) {
        logger.info("In IT AwsCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);
        response.setId(task.getId());

       // ComputeService awsService = null;
        AmazonEC2  awsService = null;
        try {
            taskLogger.set(new StringBuilder());
            if (CollectionUtils.isEmpty(task.getOpts())) {
                taskLogger.get().append("Node id  is empty ");
                response.setLogs(taskLogger.get().toString());
                return response;
            }

            Map<String, String> opts = task.getOpts();
            String nodeId = opts.get("NODE_ID");

            if (StringUtils.isEmpty(nodeId)){
                return response;
            }

            String accessKeyId = opts.get("ACCESS_KEY_ID");
            String secretKey = opts.get("SECRET_KEY");


            logger.info("Deleting bot [{}]..." , nodeId);
            taskLogger.get().append("Deleting Bot : " + nodeId);

            AmazonEC2 client = getAmazonEC2Client(accessKeyId, secretKey, getRegion(opts));


            TerminateInstancesRequest termRequest = new TerminateInstancesRequest();
            termRequest.withInstanceIds(nodeId);

            client.terminateInstances(termRequest);

            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());

            return response;

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } finally {
//            if (awsService != null){
//                awsService.getContext().close();
//            }
        }

        return response;

    }

    private String getRegion(Map<String, String> opts) {
        String region =  opts.get("REGION");
        if (StringUtils.isEmpty(region)) {
            region = FXLABS_AWS_DEFAULT_REGION;
        }
        return region;
    }

    private String getInstanceTag(Map<String, String> opts) {
        String tag =  opts.get("INSTANCE_NAME");
        if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(tag, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(tag)){
            tag = "";
        }
        return tag;
    }

    private AmazonEC2 getAmazonEC2Client(String accessKeyId, String secretKey, String region) {
        AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretKey));
        return AmazonEC2ClientBuilder.standard().withRegion(region).withCredentials(credentials).build();
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



    private  AmazonEC2 getAwsEc2Service(String accessKeyId, String secretKey, String region) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId,secretKey);

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("us-west-1")
                .withRegion(region)
                .build();

        return ec2Client;
    }

//    private ComputeService getAwsService(String accessKeyId, String secretKey) {
//
//        Properties properties = new Properties();
//
//        long scriptTimeout = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES);
//        properties.setProperty(TIMEOUT_SCRIPT_COMPLETE, scriptTimeout + "");
//
//
//        Iterable<Module> modules = ImmutableSet.<Module>of(
//                new SshjSshClientModule(),
//                new SLF4JLoggingModule(),
//                new EnterpriseConfigurationModule());
//
//        ContextBuilder builder = ContextBuilder.newBuilder("aws-ec2")
//                .credentials(accessKeyId, secretKey)
//                .modules(modules)
//                .overrides(properties);
//        System.out.print(builder.getApiMetadata());
//        ComputeService client = builder.buildView(ComputeServiceContext.class).getComputeService();
//
//        return client;
//    }

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
            return FXLABS_AWS_DEFAULT_SECURITY_GROUP_ID;
        }
        return value;
    }

    /**
     *
     * @param opts
     * @return security group
     */
    private String getSubnet(Map<String, String> opts){
        String value = opts.get("SUBNET");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return FXLABS_AWS_DEFAULT_SUBNET_ID;
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

    private static String getBotConfigScript(Map<String, String> opts){
        String value = opts.get("COMMAND");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return "";
        }

        return value;
    }

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
    //@Override
//    public CloudTaskResponse createJcloud(final CloudTask task) {
//        logger.info("In IT AwsCloud Service for task [{}]", task.getType().toString());
//
//        CloudTaskResponse response = new CloudTaskResponse();
//        response.setSuccess(false);
//        response.setId(task.getId());
//        ComputeService awsService = null;
//        try {
//
//            taskLogger.set(new StringBuilder());
//
//            if (CollectionUtils.isEmpty(task.getOpts())) {
//                return response;
//            }
//
//            Map<String, String> opts = task.getOpts();
//
//            // Args
//            String accessKeyId = opts.get("ACCESS_KEY_ID");
//            String secretKey = opts.get("SECRET_KEY");
//
//            awsService = getAwsService(accessKeyId, secretKey);
//
//            TemplateBuilder templateBuilder = awsService.templateBuilder();
//
//            //Instance type/size
//            String hardware = getInstanceType(task);
//            taskLogger.get().append("setting hardware id " + hardware);
//            templateBuilder.hardwareId(hardware);
//
//            String image = getImage(task);
//            taskLogger.get().append("Setting image id : " + image);
//
//            templateBuilder.imageId(image);
//
//            Template template = templateBuilder.build();
//
//            TemplateOptions options = template.getOptions();
//
//            String awsPrivateKeyName = getAwsPrivateKey(opts);
//            taskLogger.get().append("Setting Keypair " + awsPrivateKeyName);
//            options.as(AWSEC2TemplateOptions.class).keyPair(awsPrivateKeyName);
//
//
//            String securityGroup = getSecurityGroup(opts);
//            taskLogger.get().append("Setting Security Group " + securityGroup);
//            options.as(AWSEC2TemplateOptions.class).securityGroupIds(securityGroup);
//
//            String network = getNetwork(opts);
//            taskLogger.get().append("Setting Network " + network);
//            options.as(AWSEC2TemplateOptions.class).networks(network);
//
//
//            String username = getAwsImageUsername(opts);
//            String password = getImagePassword(opts);
//
//
//            if (!skipBotInstallation(opts)) {
//                taskLogger.get().append("Installing Bot.....");
//                LoginCredentials login = null;
//
//                if (!StringUtils.isEmpty(username)) {
//                    login = LoginCredentials.builder().user(username).privateKey(password).build();
//                } else {
//                    login = LoginCredentials.builder().privateKey(password).build();
//                }
//
//                if (login != null) {
//                    options.runAsRoot(true).runScript(getBotInstallationScript(opts)).overrideLoginCredentials(login);
//                }
//            }
//            NodeMetadata virtualBot = getOnlyElement(awsService.createNodesInGroup("fxlabs", 1, template));
//            response.setSuccess(true);
//            response.setResponseId(virtualBot.getId());
//
//            response.setLogs(taskLogger.get().toString());
//            return response;
//        } catch (RunNodesException ex) {
//            logger.warn(ex.getLocalizedMessage(), ex);
//            taskLogger.get().append(ex.getLocalizedMessage());
//            response.setLogs(taskLogger.get().toString());
//        } catch (Exception ex) {
//            logger.warn(ex.getLocalizedMessage(), ex);
//            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
//        } finally {
//            if (awsService != null) {
//                awsService.getContext().close();
//            }
//        }
//
//        return response;
//
//    }


}
