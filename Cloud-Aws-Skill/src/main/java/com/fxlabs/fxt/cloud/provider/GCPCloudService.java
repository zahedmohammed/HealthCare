package com.fxlabs.fxt.cloud.provider;


import com.amazonaws.services.ec2.model.InstanceType;
import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.ComputeScopes;
import com.google.api.services.compute.model.*;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * @author Mohammed Shoukath Ali
 */

@Component("gcpCloudService")
public class GCPCloudService implements CloudService {


    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FXLABS_AWS_DEFAULT_INSTANCE_TYPE = InstanceType.T2Micro.toString();
    private static final String AWS_PKEY = "fx-kp";
    private static final String FXLABS_GCP_DEFAULT_IMAGE = "ubuntu-os-cloud/global/images/ubuntu-1604-xenial-v20181004";

    private static final String SOURCE_IMAGE_PREFIX = "https://www.googleapis.com/compute/v1/projects/";
    private static final String SOURCE_IMAGE_PATH =
            "ubuntu-os-cloud/global/images/ubuntu-1604-xenial-v20181004";

    /** Set the Network configuration values of the sample VM instance to be created. */
    private static final String NETWORK_INTERFACE_CONFIG = "ONE_TO_ONE_NAT";
    private static final String NETWORK_ACCESS_CONFIG = "External NAT";

    private static final String APPLICATION_NAME = "FX-Bots";

    protected final static String SPACE = " ";

    final static String SLASH = "/";
    private static final String FXLABS_GCP_DEFAULT_REGION = "us-east1-b";

    private static final String FX_BOT_RESOURCES_PREFIX = "FX-";

    private static FileDataStoreFactory dataStoreFactory;

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport httpTransport;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();


    /**
     * <p>
     * This method does only one thing.
     * 1. Creates Vm's on GCP
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
        logger.info("In Cloud Skill GCP Service for task [{}]", task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        response.setSuccess(false);
        response.setId(task.getId());
        taskLogger.set(new StringBuilder());

        if (task == null || CollectionUtils.isEmpty(task.getOpts())) {
            taskLogger.get().append("Options empty for takd id :" + task.getId());
            logger.info("Options empty for task id : [{}]", task.getId());
            return response;
        }
        String resourceGroupName = null;
        StringBuilder sb = new StringBuilder();
        try {

            // Authorization
            //Credential credential = authorize("829255892794-b58sci7gg41p17b8bkpc65d2eu3como0.apps.googleusercontent.com", "WUK7w6cna-_31QB_Az7nJ2cj");
            Credential credential = authorize(getIdentity(task.getOpts()), getCredential(task.getOpts()));

            // Create compute engine object for listing instances
            Compute compute = new Compute.Builder(
                    httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
                    .setHttpRequestInitializer(credential).build();

            Instance instance = new Instance();

            resourceGroupName = getIAMRole(task.getOpts());

            String tag_ = getInstanceTag(task.getOpts());

            String region = getRegion(task.getOpts());

            String instanceName = FX_BOT_RESOURCES_PREFIX  + tag_ + region;

            if (StringUtils.isEmpty(resourceGroupName)) {
                taskLogger.get().append(String.format("Invalid project id [{}]", resourceGroupName));
                logger.info("Invalid project id [{}]", resourceGroupName);
                return response;
            }

            String config1 = getConfig(task.getOpts());
            int count = getCount(task.getOpts());

            taskLogger.get().append("VM count  : " + count);
            boolean firstIterationFlag = true;



            for (int i = 0; i < count; i++) {
                String instanceNameWithCount = instanceName + i;
                instance.setName(instanceNameWithCount);
                instance.setMachineType(
                        "https://www.googleapis.com/compute/v1/projects/"
                                + resourceGroupName + "/zones/" + region + "/machineTypes/n1-standard-1");


                // Add Network Interface to be used by VM Instance.
                NetworkInterface ifc = new NetworkInterface();
                ifc.setNetwork("https://www.googleapis.com/compute/v1/projects/" + resourceGroupName + "/global/networks/default");
                List<AccessConfig> configs = new ArrayList<>();
                AccessConfig config = new AccessConfig();
                config.setType(NETWORK_INTERFACE_CONFIG);
                config.setName(NETWORK_ACCESS_CONFIG);
                configs.add(config);

                taskLogger.get().append(String.format("Adding Network Interface [{}] to be used by VM [{}]", ifc.toPrettyString(), instanceNameWithCount));
                logger.info("Adding Network Interface [{}]", ifc.toPrettyString());

                ifc.setAccessConfigs(configs);
                instance.setNetworkInterfaces(Collections.singletonList(ifc));


                // Add attached Persistent Disk to be used by VM Instance.
                AttachedDisk disk = new AttachedDisk();
                disk.setBoot(true);
                disk.setAutoDelete(true);
                disk.setType("PERSISTENT");
                AttachedDiskInitializeParams params = new AttachedDiskInitializeParams();
                // Assign the Persistent Disk the same name as the VM Instance.
                params.setDiskName(instanceNameWithCount);
                // Specify the source operating system machine image to be used by the VM Instance.
                params.setSourceImage(SOURCE_IMAGE_PREFIX + SOURCE_IMAGE_PATH);
                taskLogger.get().append(String.format("Operating system image [{}] to be used by VM [{}]", SOURCE_IMAGE_PATH, instanceNameWithCount));
                logger.info("Add attached Persistent Disk [{}] to be used by VM [{}]", disk.toPrettyString(), instanceNameWithCount);


                // Specify the disk type as Standard Persistent Disk
                params.setDiskType("https://www.googleapis.com/compute/v1/projects/" + resourceGroupName + "/zones/"
                        + region + "/diskTypes/pd-standard");
                disk.setInitializeParams(params);
                taskLogger.get().append(String.format("Add attached Persistent Disk [{}] to be used by VM [{}]", disk.toPrettyString(), instanceNameWithCount));
                logger.info("Add attached Persistent Disk [{}] to be used by VM [{}]", disk.toPrettyString(), instanceNameWithCount);
                instance.setDisks(Collections.singletonList(disk));

                // Initialize the service account to be used by the VM Instance and set the API access scopes.
                ServiceAccount account = new ServiceAccount();
                account.setEmail("default");
                List<String> scopes = new ArrayList<>();
                scopes.add("https://www.googleapis.com/auth/devstorage.full_control");
                scopes.add("https://www.googleapis.com/auth/compute");
                account.setScopes(scopes);

                taskLogger.get().append(String.format("Adding Scopes [{}] to be used by VM [{}]", scopes.toArray().toString(), instanceNameWithCount));
                logger.info("Adding Scopes [{}] to be used by VM [{}]", scopes.toArray().toString(), instanceNameWithCount);
                instance.setServiceAccounts(Collections.singletonList(account));


                // Optional - Add a startup script to be used by the VM Instance.
                Metadata meta = new Metadata();
                Metadata.Items item = new Metadata.Items();
                item.setKey("startup-script");
                // item.setValue("#! /bin/bash\n\n# Begin\n#install docker\nsudo curl -sSL https://get.docker.com/ | sh\n" + "sudo docker run -d -e FX_IAM=Mwc/0zF7dfX+PUq6Jz26AkdbFUE13eL5 -e FX_KEY=x8/W2WMZ2Cn0/5b86Aj1HEJPEoNGTnU0QhbDH7aTS1EhN9E6+/P87g== fxlabs/bot:latest");
                item.setValue("#! /bin/bash\n\n# Begin\n#install docker\nsudo curl -sSL https://get.docker.com/ | sh\n" + config1);
                meta.setItems(Collections.singletonList(item));

                taskLogger.get().append(String.format("Startup script [{}] to be used by VM [{}]",config1, instanceNameWithCount));
                logger.info("Startup script [{}] to be used by VM [{}]",config1, instanceNameWithCount);
                instance.setMetadata(meta);

                System.out.println(instance.toPrettyString());
                Compute.Instances.Insert insert = compute.instances().insert(resourceGroupName, region, instance);
                Operation execute = insert.execute();
                if (!firstIterationFlag) {
                    sb.append("," + instanceNameWithCount);
                } else {
                    sb.append(instanceNameWithCount);
                }
            }

            response.setSuccess(true);
            if (!StringUtils.isEmpty(sb.toString())) {
                response.setResponseId(sb.toString());
            }
            response.setLogs(taskLogger.get().toString());

            return response;
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
            response.setLogs(taskLogger.get().toString());
            if (!StringUtils.isEmpty(sb.toString())) {
                response.setResponseId(sb.toString());
            }
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

            // Authorization
            Credential credential = authorize("829255892794-b58sci7gg41p17b8bkpc65d2eu3como0.apps.googleusercontent.com", "WUK7w6cna-_31QB_Az7nJ2cj");

            // Create compute engine object for listing instances
            Compute compute = new Compute.Builder(
                    httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
                    .setHttpRequestInitializer(credential).build();

            String region = getRegion(task.getOpts());

            String resourceGroupName = getIAMRole(task.getOpts());

            String[] instanceIds = nodeIds.split(",");
            List<String> list = Arrays.asList(instanceIds);

            list.forEach(instance -> {

                // delete VM,
                try {
                    logger.info("Deleting virtual machine [{}]", instance);
                    taskLogger.get().append("Deleting virtual machine " + instance);
                    Compute.Instances.Delete delete =
                            compute.instances().delete(resourceGroupName, region, instance);
                } catch (Exception e) {
                    logger.info("Failed to delete virtual Machine [{}]", instance);
                    taskLogger.get().append("Failed to delete virtual Machine " + instance);
                }
            });


            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());

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
            region = FXLABS_GCP_DEFAULT_REGION;
        }
        return region;
    }

    private String getConfig(Map<String, String> opts) {
        String config = opts.get("COMMAND");
        return config;
    }


    private String getInstanceTag(Map<String, String> opts) {
        String tag = opts.get("INSTANCE_NAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(tag, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(tag)) {
            tag = "";
        }
        return tag;
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
     * @return image id
     */
    protected String getImageUsername(Map<String, String> map) {
        String val = map.get("IMAGE_USERNAME");
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(val, "null")) {
            return "";
        }
        return val;
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private static Credential authorize(String cliendId, String clientSecret) throws Exception {
        // initialize client secrets object
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
        // load client secrets
        GoogleClientSecrets.Details details = new GoogleClientSecrets.Details();
        details.setClientId(cliendId);
        details.setClientSecret(clientSecret);
        clientSecrets.setWeb(details);
        /**
         * OAuth 2.0 scopes
         */
         final List<String> SCOPES = Arrays.asList(ComputeScopes.COMPUTE);
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow;
        flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(dataStoreFactory)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
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
        String val = map.get("ACCESS_KEY_ID");
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
        String password = map.get("SECRET_KEY");
//        map.remove("PASSWORD");
        return password;
    }

}
