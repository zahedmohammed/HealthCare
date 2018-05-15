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
import java.util.*;


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
    private static final String AWS_PKEY = "fx-pk";
    //private static final String FXLABS_AWS_DEFAULT_IMAGE = "ami-09d2fb69";
    private static final String FXLABS_AWS_DEFAULT_IMAGE = "Ubuntu 16.04";

    private static final String FXLABS_DEFAULT_SECURITY_GROUP = "fx-sg";
    private static final String FXLABS_DEFAULT_SUBNET = "fx-subnet";

    private static final String FXLABS_AWS_DEFAULT_REGION = "us-west-1";

    private static final String FXLABS_AWS_DEFAULT_VPC = "fx-vpc";

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

            String image = getImageId(opts, awsService);
            taskLogger.get().append("Setting image id : " + image);

            String awsPrivateKeyName = getAwsPrivateKey(opts);
            taskLogger.get().append("Setting Keypair " + awsPrivateKeyName);

            String securityGroupId = getSecurityGroupId(opts,awsService);
            taskLogger.get().append("Setting Security Group Id " + securityGroupId);

            String subnetId = getSubnetId(opts, awsService);
            taskLogger.get().append("Setting Subnet Id " + securityGroupId);

//            String network = getNetwork(opts);
//            taskLogger.get().append("Setting Network " + network);
//
//            String username = getAwsImageUsername(opts);
//            String password = getImagePassword(opts);

            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withImageId(image)
                    .withInstanceType(com.amazonaws.services.ec2.model.InstanceType.T2Small)
                    .withMinCount(1).withMaxCount(1)
                    .withKeyName(awsPrivateKeyName)
                  //  .withSubnetId(subnetId)
                    .withSecurityGroupIds(securityGroupId);

            //findby image name
            //findbu by subnet name
            // findby securit\y groupname
            //fx-pk hard code
            //fx-subnet hard code(UI also)
            //fx-sg hardcode
            // Documentation

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

//    /**
//     *
//     * @param opts
//     * @return imagepassword
//     */
//    private String getImagePassword(Map<String, String> opts){
//
//        String value = opts.get("IMAGE_PASSWORD");
//
//        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
//                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
//            return AWS_PRIVATE_KEY_PEM;
//        }
//        return value;
//    }

//    /**
//     *
//     * @param opts
//     * @return security group
//     */
//    private String getSubnet(Map<String, String> opts){
//        String value = opts.get("SUBNET");
//
//        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
//                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
//            return FXLABS_AWS_DEFAULT_SUBNET_ID;
//        }
//        return value;
//    }

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

    private String getImageId(Map<String, String> opts, AmazonEC2 awsService) {

        String imageName = opts.get("IMAGE");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(imageName, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(imageName)) {
            imageName = FXLABS_AWS_DEFAULT_IMAGE;
        }

        DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest()
                .withFilters(new Filter().withName("name").withValues(imageName));

        DescribeImagesResult result = awsService.describeImages(describeImagesRequest);
        List<Image> images = result.getImages();
        for (Image image : images) {
            return image.getImageId();
        }

        return null;
    }

    private String getSubnetId(Map<String, String> opts, AmazonEC2  awsService){

        String subnet =  opts.get("SUBNET");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(subnet, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(subnet)) {
            subnet = FXLABS_DEFAULT_SUBNET;
        }

        DescribeSubnetsRequest request = new DescribeSubnetsRequest()
                .withFilters(new Filter().withName("tag-value").withValues(subnet));
        DescribeSubnetsResult response = awsService.describeSubnets(request);
        List<Subnet> subnets = response.getSubnets();
        for (Subnet subnet_ : subnets) {
//            System.out.println(subnet.getSubnetId() + "==" + subnet.getVpcId()
//                    + "==" + subnet.getTags());
            return subnet_.getSubnetId();
        }

        return null;
    }


    /**
     *
     * @param opts
     * @return security group
     */
    private String getSecurityGroupId(Map<String, String> opts, AmazonEC2  awsService){
        String value = opts.get("SECURITY_GROUP");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
             value = FXLABS_DEFAULT_SECURITY_GROUP;
        }

        DescribeSecurityGroupsRequest req = new DescribeSecurityGroupsRequest()
                .withFilters(new Filter().withName("tag-value").withValues(value));

        DescribeSecurityGroupsResult result = awsService.describeSecurityGroups(req);

        List<SecurityGroup> sgs = result.getSecurityGroups();

        for (SecurityGroup sg_ : sgs) {
//            System.out.println(subnet.getSubnetId() + "==" + subnet.getVpcId()
//                    + "==" + subnet.getTags());
            return sg_.getGroupId();
        }


        return null;
    }


}
