package com.fxlabs.fxt.cloud.azure.skill;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.*;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.LogLevel;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class AzureCloudService implements CloudService {

    final Logger logger = LoggerFactory.getLogger(getClass());
    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    /**
     * <p>
     *  This method does only one thing
     *   1. Creates Vm's on Azure
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
        logger.info("In AzureCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();
        try {
            Azure azure = connect(task);
            String vmName =  "VM Name";
            Region region = Region.US_WEST;
            String resourceGroup = "FXLabs";
            String rootUser = RandomStringUtils.randomAlphabetic(6);  //"fxroot";  // Random generation
            String rootPwd = RandomStringUtils.randomAlphabetic(10); // Rndom
            KnownLinuxVirtualMachineImage ubuntuImage = KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS;
            VirtualMachineSizeTypes size = VirtualMachineSizeTypes.STANDARD_D1; //  .5 GB VM

            VirtualMachine linuxVM = azure.virtualMachines()
                    .define("FX_"+vmName)
                    .withRegion(region)
                    .withExistingResourceGroup(resourceGroup)
                    .withNewPrimaryNetwork("10.0.0.0/28")   // Pick default network,NO IP
                    .withPrimaryPrivateIPAddressDynamic()
                    .withoutPrimaryPublicIPAddress()
                    .withPopularLinuxImage(ubuntuImage )
                    .withRootUsername(rootUser)
                    .withRootPassword(rootPwd)
                    .withSize(size)
                    .create();
            response.setSuccess(true);
            response.setKeys(linuxVM.id());


        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return response;

    }


    /**
     * <p>
     *  This method does only one thing
     *   1. destroy Vm's on Azure
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
        logger.info("In AzureCloud Service for task [{}]" , task.getType().toString());

        CloudTaskResponse response = new CloudTaskResponse();

        String vmId = null; //TODO: task.getVMId();

        try {

            Azure azure = connect(task);
            azure.virtualMachines().deleteByIdAsync(vmId);
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

    private Azure connect(CloudTask task){

        Azure azure = null;

        String url = task.getOpts().get("URL");
        String userName = task.getOpts().get("USERNAME");
        String secretKey = task.getOpts().get("PASSWORD");
        String domain = task.getOpts().get("DOMAIN");
        String image = task.getOpts().get("IMAGE");
        String subscription = task.getOpts().get("SUBSCRIPTION");

        try {
            ApplicationTokenCredentials crd = new ApplicationTokenCredentials(userName,domain,secretKey, AzureEnvironment.AZURE);
            if (subscription == null) {
                azure = Azure.configure()
                        .withLogLevel(LogLevel.BASIC)
                        .authenticate(crd)
                        .withDefaultSubscription();
            }else{
                azure = Azure.configure()
                        .withLogLevel(LogLevel.BASIC)
                        .authenticate(crd)
                        .withSubscription(subscription);
            }
        } catch (IOException e) {
            //TODO: return error
            e.printStackTrace();
        }

        return azure;
    }

}
