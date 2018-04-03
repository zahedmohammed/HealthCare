package com.fxlabs.fxt.vc.git.skill;

import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.vc.git.skill.services.Task;
import com.fxlabs.fxt.vc.git.skill.services.VersionControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SVNService implements VersionControlService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    /**
     * <p>
     * This method does only one thing.
     * 1. Checkout the repository files to the given location.
     * <p>
     * At the end of the execution all the project files should in the given path.
     * Or the response should indicate the processing failed by setting the Response.success to false along with the
     * error message.
     * </p>
     *
     * @param task <p>
     *             Contains Version-Control System connection information.
     *             e.g.
     *             url - contains the VC endpoint
     *             username - VC username/access-key
     *             password - VC password/secret-key
     *             branch - Repository branch defaults to master.
     *             </p>
     * @param path <p>
     *             File location where to check-out the version-control project.
     *             </p>
     * @return <p>
     * VCTaskResponse - Should only set these properties.
     * 1. success - true/false
     * 2. logs - execution or error logs.
     * </p>
     */
    @Override
    public VCTaskResponse process(final Task task, final String path) {

        VCTaskResponse response = new VCTaskResponse();
        response.setSuccess(false);

        try {
            taskLogger.set(new StringBuilder());

            // TODO - Checkout files


            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());


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
    public String push(String path, String username, String password) {
        return null;
    }

}
