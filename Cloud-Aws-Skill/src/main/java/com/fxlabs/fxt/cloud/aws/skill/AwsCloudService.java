package com.fxlabs.fxt.cloud.aws.skill;

import com.fxlabs.fxt.cloud.skill.services.CloudService;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 *
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class AwsCloudService implements CloudService {

    final Logger logger = LoggerFactory.getLogger(getClass());
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

}
