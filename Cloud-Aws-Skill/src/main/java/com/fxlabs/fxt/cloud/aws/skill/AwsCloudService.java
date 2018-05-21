package com.fxlabs.fxt.cloud.aws.skill;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
//import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
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
    private static final String FXLABS_AWS_DEFAULT_INSTANCE_TYPE = InstanceType.T2Micro.toString();
    private static final String AWS_PKEY = "fx-pk";
    private static final String FXLABS_AWS_DEFAULT_IMAGE = "ubuntu/images/hvm-ssd/ubuntu-xenial-16.04-amd64-server-*";

    private static final String FXLABS_DEFAULT_SECURITY_GROUP = "fx-sg";
    private static final String FXLABS_DEFAULT_SUBNET = "fx-subnet";

    private static final String FXLABS_AWS_DEFAULT_REGION = "us-west-1";

//    static final Map<String, String> REGION_ENPOINTS = ImmutableMap.<String, String>builder()
//           .put("us-east-1", "ec2.us-east-1.amazonaws.com")
//            .put("us-west-1", "ec2.us-west-1.amazonaws.com").build();

  //  private static final String FXLABS_AWS_DEFAULT_VPC = "fx-vpc";

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
           // String region = "us-east-1";

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

            if (StringUtils.isEmpty(securityGroupId)){
                logger.info("Security Group not found for region " + region);
                taskLogger.get().append("Security group with group-name [" + FXLABS_DEFAULT_SECURITY_GROUP  + "] not found in region" + region);
                response.setLogs(taskLogger.get().toString());
                return response;
            }

            String subnetId = getSubnetId(opts, awsService);
            taskLogger.get().append("Setting Subnet Id " + subnetId);

            if (StringUtils.isEmpty(subnetId)){
                logger.info("Subnet not found for region " + region);
                taskLogger.get().append("Subnet with tag value [" + FXLABS_DEFAULT_SUBNET + "] not found in region " + region);
                response.setLogs(taskLogger.get().toString());
                return response;
            }


            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withImageId(image)
                    .withInstanceType(com.amazonaws.services.ec2.model.InstanceType.T2Small)
                    .withMinCount(1).withMaxCount(1)
                    .withKeyName(awsPrivateKeyName)
                    .withSubnetId(subnetId)
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
            response.setLogs(taskLogger.get().toString());
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

            AmazonEC2 client = getAwsEc2Service(accessKeyId, secretKey, getRegion(opts));


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

//    private AmazonEC2 getAmazonEC2Client(String accessKeyId, String secretKey, String region) {
//        AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretKey));
//        return AmazonEC2ClientBuilder.standard().withRegion(region).withCredentials(credentials).build();
//    }

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


    private AmazonEC2 getAwsEc2Service(String accessKeyId, String secretKey, String region) {

        logger.info("Signing in region [{}]", region);
        taskLogger.get().append("Signing in region " + region);
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);


        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                //.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(REGION_ENPOINTS.get(region), region))
                .withRegion(region)
                .build();

        return ec2Client;
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



    private static String getBotConfigScript(Map<String, String> opts){
        String value = opts.get("COMMAND");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(value, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return "";
        }

        return value;
    }

    private String getImageId(Map<String, String> opts, AmazonEC2 awsService) {

        String accessKeyId = opts.get("ACCESS_KEY_ID");
        String secretKey = opts.get("SECRET_KEY");

       // AmazonEC2 awsService_ = getAwsEc2Service(secretKey, secretKey, "us-west-1");

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

        String accessKeyId = opts.get("ACCESS_KEY_ID");
        String secretKey = opts.get("SECRET_KEY");


        String subnet_ =  opts.get("SUBNET");

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(subnet_, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(subnet_)) {
            subnet_ = FXLABS_DEFAULT_SUBNET;
        }

        DescribeSubnetsRequest request = new DescribeSubnetsRequest()
                .withFilters(new Filter().withName("tag-value").withValues(subnet_));
        DescribeSubnetsResult response = awsService.describeSubnets();
        List<Subnet> subnets = response.getSubnets();
        logger.info("Found  [{}] subnets in region [{}]", response.getSubnets().size(), getRegion(opts));
        for (Subnet subnet : subnets) {
            System.out.println(subnet.getSubnetId() + " in vpc:" + subnet.getVpcId()
                    + " with tags:" + subnet.getTags());
            for (Tag entry : subnet.getTags()) {
                if ("fx-subnet".equals(entry.getValue())) {
                    return subnet.getSubnetId();
                }
            }
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
                .withFilters(new Filter().withName("group-name").withValues(value));

        DescribeSecurityGroupsResult result = awsService.describeSecurityGroups(req);

        List<SecurityGroup> sgs = result.getSecurityGroups();
        logger.info("Found  [{}] security groups in region [{}]", sgs.size(), getRegion(opts));
        for (SecurityGroup sg_ : sgs) {
            return sg_.getGroupId();
        }


        return null;
    }

}
