package com.fxlabs.fxt.cloud.provider;


import com.amazonaws.services.ec2.model.*;
import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.network.NetworkSecurityGroup;
import com.microsoft.azure.management.network.SecurityRuleProtocol;
import com.microsoft.azure.management.storage.StorageAccount;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Mohammed Shoukath Ali
 */

@Component("azureCloudService")
public class AzureCloudService implements CloudService {


    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FXLABS_AWS_DEFAULT_INSTANCE_TYPE = InstanceType.T2Micro.toString();
    private static final String AWS_PKEY = "fx-kp";
    private static final String FXLABS_AWS_DEFAULT_IMAGE = "ubuntu/images/hvm-ssd/ubuntu-xenial-16.04-amd64-server-*";

    private static final String FXLABS_DEFAULT_SECURITY_GROUP = "fx-sg";
    private static final String FXLABS_DEFAULT_SUBNET = "fx-subnet";

    private static final String FXLABS_AWS_DEFAULT_REGION = "eastus";

    private static final String FXLABS_DEFAULT_SCRIPT_URL = "https://raw.githubusercontent.com/fxlabsinc/FX-Bot-Script/master/fx_bot_install_script.sh";


    final String linuxCustomScriptExtensionName = "CustomScriptForLinux";
    final String linuxCustomScriptExtensionTypeName = "CustomScriptForLinux";
    final String linuxCustomScriptExtensionVersionName = "1.4";  //"1.4";
    final String linuxCustomScriptExtensionPublisherName = "Microsoft.OSTCExtensions";

    protected final static String SPACE = " ";

    final static String SLASH = "/";


    private static final String FX_BOT_RESOURCES_PREFIX = "FX-";

//    static final Map<String, String> REGION_ENPOINTS = ImmutableMap.<String, String>builder()
//           .put("us-east-1", "ec2.us-east-1.amazonaws.com")
//            .put("us-west-1", "ec2.us-west-1.amazonaws.com").build();

    //  private static final String FXLABS_AWS_DEFAULT_VPC = "fx-vpc";

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();


    /**
     * <p>
     * This method does only one thing.
     * 1. Creates Vm's on AWS
     * </p>
     *
     * @param task <p>
     *             Contains public cloud system connection information.
     *             e.g.
     *             VM configuaration
     *             </p>
     * @return <p>
     * CloudTaskResponse - Should only set these properties.
     * 1. success - true/false
     * 2. logs - execution or error logs.
     * </p>
     */
    @Override
    public CloudTaskResponse create(final CloudTask task) {
        logger.info("In Cloud Skill AZURE Service for task [{}]", task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);
        response.setId(task.getId());
        taskLogger.set(new StringBuilder());

        if (task == null || CollectionUtils.isEmpty(task.getOpts())) {
            taskLogger.get().append("Options empty for takd id :" + task.getId());
            logger.info("Options empty for task id : [{}]", task.getId());
            return response;
        }

//        String subscriptionId = "10cf3790-fa0d-4747-bf4d-8f5fbfe71d69";
//        String tenant = "a3997700-5799-4d9e-b1b4-6744db57e9c7";
//        String client = "92349a66-575b-4916-bf9c-caa6dae920da";
//        String key = "HCSLxh7TOADqsQywNyeXDWpSSsLCkBsc2mPKRgpZWJg=";
        try {
            Azure azure = getAzureInstance(task.getOpts());
            String resourceGroupName = getIAMRole(task.getOpts());

            String tag_ = getInstanceTag(task.getOpts());

            String region = getRegion(task.getOpts());

            String tag = FX_BOT_RESOURCES_PREFIX + "-" + tag_ + region;

            if (StringUtils.isEmpty(resourceGroupName)) {
                resourceGroupName = tag + "-rg";
            }
            //String resourceGroupName = "dev";

		    // create RG
            taskLogger.get().append("Resource group :" + resourceGroupName);
            logger.info("Creating resource group :" + resourceGroupName);
            azure.resourceGroups().define(resourceGroupName)
                    .withRegion(com.microsoft.azure.management.resources.fluentcore.arm.Region.fromName(region))
                    .create();

            // create network and subnet
            String vn = FX_BOT_RESOURCES_PREFIX + region + "-vnet";
            String subnet = FX_BOT_RESOURCES_PREFIX + region + "-subnet";

            logger.info("Creating virtual network :" + vn);
            Network network = createNetwork(region, azure, resourceGroupName, vn, subnet);
            com.microsoft.azure.management.network.Subnet subnet1 = getSubnet(network, subnet);

            taskLogger.get().append("Setting Subnet " + subnet1.name());

            //NSG
            String nsgName = FX_BOT_RESOURCES_PREFIX + region + "-nsg";
            logger.info("Creating security group :" + nsgName);

            com.microsoft.azure.management.network.NetworkSecurityGroup nsg = createNetworkSecurityGroup(region, azure, resourceGroupName, "", nsgName);


            int count = getCount(task.getOpts());

            taskLogger.get().append("VM count  : " + count);
            boolean firstIterationFlag = true;

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < count; i++) {

                String nicName = tag + i  + "-nic";

                com.microsoft.azure.management.network.NetworkInterface networkInterface1 = azure.networkInterfaces().define(nicName)
                        .withRegion(region)
                        .withExistingResourceGroup(resourceGroupName)
                        .withExistingPrimaryNetwork(network)
                        .withSubnet(subnet)
                        .withPrimaryPrivateIPAddressDynamic()
                        //.withNewPrimaryPublicIPAddress(ip)
                        .withIPForwarding()
                        .withExistingNetworkSecurityGroup(nsg)
                        .create();

                // OSDisk Size
                String osDiskName = tag + i + "-osddisk";

                String sa = org.apache.commons.lang3.StringUtils.replace(FX_BOT_RESOURCES_PREFIX + tag_, "-", "") + i + "sa";

                StorageAccount storageAccount = createStorageAccount(region, azure, resourceGroupName, sa);

                taskLogger.get().append("Setting image  : " + KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS);
                taskLogger.get().append("Setting image  : " + VirtualMachineSizeTypes.STANDARD_D3_V2);


                VirtualMachine vm = null;
                try {
                    vm = azure.virtualMachines().define(tag + i)
                            .withRegion(region)
                            .withNewResourceGroup(resourceGroupName)
                            .withExistingPrimaryNetworkInterface(networkInterface1)
                            .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                            .withRootUsername("fxlabs" + RandomStringUtils.randomAlphanumeric(4))
                            .withRootPassword(RandomStringUtils.randomAlphanumeric(8) + "Fxbot!")
                            .withComputerName(tag + i)
                            .withOSDiskName(osDiskName)
                            .withExistingStorageAccount(storageAccount)
                            .withSize(VirtualMachineSizeTypes.STANDARD_A1)
                            .withBootDiagnostics()
                            .create();
                } catch (Exception e) {
                    logger.info(e.getLocalizedMessage());
                    response.setResponseId(resourceGroupName);
                }

                if (vm != null) {
                    if (!firstIterationFlag) {
                        sb.append("," + tag + i);
                    } else {
                        sb.append(tag + i);
                    }
                    firstIterationFlag = false;

                    try {
                        final String botInstallScript = getBotScriptURL(task.getOpts());
                        final String AZURE_CUSTOM_SCRIPT_CMD = "bash fx_bot_install_script.sh";

                        String runBot = AZURE_CUSTOM_SCRIPT_CMD + SPACE + getAzureConfig(task.getOpts());
                        //String runBot = AZURE_CUSTOM_SCRIPT_CMD + SPACE + "cloud.fxlabs.io" + SPACE + "5671" + SPACE + "true" + SPACE + "Mwc/0zF7dfX+PUq6Jz26AkdbFUE13eL5" + SPACE + "jrtPsZ5x96rhW6H/5zsFPFH8XzDmIq5/ldLAjyOZbbE=" + SPACE + "latest";
                        logger.info("Azure bot execution script :" + runBot);

                        final List<String> linuxScriptFileUris = new ArrayList<>();
                        linuxScriptFileUris.add(botInstallScript);


                        vm.update()
                                .defineNewExtension(linuxCustomScriptExtensionName)
                                .withPublisher(linuxCustomScriptExtensionPublisherName)
                                .withType(linuxCustomScriptExtensionTypeName)
                                .withVersion(linuxCustomScriptExtensionVersionName)
                                .withMinorVersionAutoUpgrade()
                                .withPublicSetting("fileUris", linuxScriptFileUris)
                                .withPublicSetting("commandToExecute", runBot)
                                .attach()
                                .apply();
                    } catch (Exception e) {
                        logger.info("Exception configuring bot :" + e.getMessage());
                        taskLogger.get().append("exception occured configuring bot  : " + e.getStackTrace());
                    }
                }
            }

            response.setSuccess(true);
            if (resourceGroupName.startsWith(FX_BOT_RESOURCES_PREFIX) && resourceGroupName.endsWith("-rg")) {
                response.setResponseId(resourceGroupName);
            } else {
                response.setResponseId(sb.toString());
            }
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
        logger.info("In Cloud Skill AZURE Service for task [{}]", task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);
        response.setId(task.getId());

        try {
            taskLogger.set(new StringBuilder());
            if (CollectionUtils.isEmpty(task.getOpts())) {
                taskLogger.get().append("Node id  is empty ");
                response.setLogs(taskLogger.get().toString());
                return response;
            }

            Map<String, String> opts = task.getOpts();
            String nodeIds = opts.get("NODE_ID");

            if (StringUtils.isEmpty(nodeIds)) {
                return response;
            }

            String tag = getInstanceTag(task.getOpts());

            String region = getRegion(task.getOpts());

            String vmName = FX_BOT_RESOURCES_PREFIX + "-" + tag + region;

            String resourceGroupName = vmName + "-rg";


            Azure azure = getAzureInstance(task.getOpts());

            if (resourceGroupName.startsWith(FX_BOT_RESOURCES_PREFIX) && resourceGroupName.endsWith("-rg")) {
                try {
                    logger.info("deleting resource group [{}]", nodeIds);
                    taskLogger.get().append("deleting resource group : " + nodeIds);
                    azure.resourceGroups().deleteByName(nodeIds);
                } catch (Exception e) {
                    logger.info("Failed to delete resource group [{}]", nodeIds + " " + e.getLocalizedMessage());
                    taskLogger.get().append("Failed to delete resource group : " + nodeIds + " " + e.getLocalizedMessage());
                }
            } else {
                String[] instanceIds = nodeIds.split(",");
                List<String> list = Arrays.asList(instanceIds);
                list.forEach(instance -> {

                    // delete VM,
                    try {
                        logger.info("Deleting virtual machine [{}]", instance);
                        taskLogger.get().append("Deleting virtual machine " + instance);
                        azure.virtualMachines().deleteByResourceGroup(resourceGroupName, instance);
                    } catch (Exception e) {
                        logger.info("Failed to delete virtual Machine [{}]", instance);
                        taskLogger.get().append("Failed to delete virtual Machine " + instance);
                    }
                });

                // deleting resources
                list.forEach(instance -> {
                    //Nic, & ip, subnet, virtual-network, storage-account, network-security-group
                    String nicName = instance + "-nic";
                    String ipAdd = instance + "-pip";
                    String nsg = FX_BOT_RESOURCES_PREFIX + region + "-nsg";

                    String subnet = FX_BOT_RESOURCES_PREFIX + region + "-subnet";
                    String vn = FX_BOT_RESOURCES_PREFIX + region + "-vnet";
                    String sa = org.apache.commons.lang3.StringUtils.replace(FX_BOT_RESOURCES_PREFIX + instance, "-", "") + "sa";
                    String osDiskName = instance + "-osddisk";
                    // delete NIC
                    if (!org.apache.commons.lang3.StringUtils.isEmpty(nicName)) {
                        try {
                            azure.networkInterfaces().deleteByResourceGroup(resourceGroupName, nicName);
                        } catch (Exception e) {

                        }
                    }
                    // delete NSG
                    if (!org.apache.commons.lang3.StringUtils.isEmpty(nsg)) {
                        try {
                            azure.networkSecurityGroups().deleteByResourceGroup(resourceGroupName, nsg);
                        } catch (Exception e) {

                        }
                    }

                    // delete Storage account
                    if (!org.apache.commons.lang3.StringUtils.isEmpty(sa)) {
                        try {
                            azure.storageAccounts().deleteByResourceGroup(resourceGroupName, sa);
                        } catch (Exception e) {

                        }
                    }

                    azure.disks().deleteByResourceGroup(resourceGroupName, osDiskName);
                });


            }


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

    private int getCount(Map<String, String> opts) {

        String countStr = opts.get("COUNT");
        try {
            return Integer.parseInt(countStr);
        } catch (NumberFormatException ex) {
            logger.info("Error parsing bot count [{}]", countStr);
        }

        return 1;
    }

    private String getRegion(Map<String, String> opts) {
        String region = opts.get("REGION");
        if (StringUtils.isEmpty(region)) {
            region = FXLABS_AWS_DEFAULT_REGION;
        }
        return region;
    }

    private String getAzureConfig(Map<String, String> opts) {
        String config = opts.get("AZURE_LINUX_CONFIG");
        return config;
    }

    private String getBotScriptURL(Map<String, String> opts) {
        String url = opts.get("BOT_SCRIPT_URL");
        if (StringUtils.isEmpty(url)) {
            url = FXLABS_DEFAULT_SCRIPT_URL;
        }
        return url;
    }

    private String getInstanceTag(Map<String, String> opts) {
        String tag = opts.get("INSTANCE_NAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(tag, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(tag)) {
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


    /**
     * @param map
     * @return region
     */
    protected String getLocationId(Map<String, String> map) {
        String val = map.get("REGION");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }


    /**
     * @param map
     * @return zone
     */
    protected String getZone(Map<String, String> map) {
        String val = map.get("ZONE");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return group
     */
    protected String getGroupName(Map<String, String> map) {
        String val = map.get("GROUP");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return hardware/flavor id
     */
    protected String getHardwareId(Map<String, String> map) {
        String val = map.get("HARDWARE");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return hardware/flavor id
     */
    protected String getCPU(Map<String, String> map) {
        String val = map.get("CPU");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return image id
     */
    protected String getImageId(Map<String, String> map) {
        String val = map.get("IMAGE");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return image id
     */
    protected String getImageUsername(Map<String, String> map) {
        String val = map.get("IMAGE_USERNAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    private Azure getAzureInstance(Map<String, String> map) {

        try {
            String subscriptionId = map.get("SUBSCRIPTION");
            String tenant = map.get("TENANT");
            String client = getIdentity(map);
            String key = getCredential(map);

            ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(
                    client, tenant, key, AzureEnvironment.AZURE);

            Azure azure = Azure.authenticate(credentials).withSubscription(subscriptionId);

            return azure;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String getIAMRole(Map<String, String> map) {
        String val = map.get("RESOURCE_GROUP");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return username
     */
    protected String getIdentity(Map<String, String> map) {
        String val = map.get("USERNAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * @param map
     * @return passowrd
     */
    protected String getCredential(Map<String, String> map) {
        String password = map.get("PASSWORD");
//        map.remove("PASSWORD");
        return password;
    }


    private static Network createNetwork(String region, Azure azure, String resourceGroupName, String vn, String subnet) {
        Network network;
        String networkCIDR = "10.0.0.0/24";
        String subnetCIDR = "10.0.0.0/24";

        network = azure.networks().define(vn)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .withAddressSpace(networkCIDR)
                .defineSubnet(subnet)
                .withAddressPrefix(subnetCIDR)
                .attach()
                .create();
        return network;
    }

    private static StorageAccount createStorageAccount(String region, Azure azure, String resourceGroupName, String sa) {
        StorageAccount storageAccount;
//		logger.info("Creating StorageAccount [{}]", sa);
//		asyncRemoteLogger.log(String.format("Creating StorageAccount [%s]", sa));

        storageAccount = azure.storageAccounts().define(sa)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .create();
        return storageAccount;
    }

    private static com.microsoft.azure.management.network.Subnet getSubnet(Network network, String subnet) {
        for (String key : network.subnets().keySet()) {
            com.microsoft.azure.management.network.Subnet subnet_ = network.subnets().get(key);
            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(subnet, subnet_.name())) {
                return subnet_;
            }
        }
        return null;
    }

    private static NetworkSecurityGroup createNetworkSecurityGroup(String region, Azure azure, String resourceGroupName, String ip, String nsgName) {
        NetworkSecurityGroup nsg;
//		logger.info("Creating NSG [{}]", ip);
//		asyncRemoteLogger.log(String.format("Creating NSG [%s]", nsgName));
        nsg = azure.networkSecurityGroups().define(nsgName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .create();

        int priority = 100;
        // OPEN ALL OUTBOUND PORTS
//		logger.info("Adding NSG Outbound Rule for 'ALLOW_ALL");
//		asyncRemoteLogger.log(String.format("Adding NSG Outbound Rule for 'ALLOW_ALL"));
        nsg.update().defineRule("ALLOW_ALL")
                .allowOutbound()
                .fromAnyAddress()
                .fromAnyPort()
                .toAnyAddress()
                .toAnyPort()
                .withProtocol(SecurityRuleProtocol.TCP)
                .withDescription("Allow All Outbound Communication.")
                .withPriority(priority++)
                .attach()
                .apply();

        return nsg;
    }


}
