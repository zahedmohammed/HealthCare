package com.fxlabs.fxt.vc.git.skill.services;

import com.fxlabs.fxt.dto.vc.VCTaskResponse;

/**
 * @author Intesar Shannan Mohammed
 */
public interface VersionControlService {

    /**
     * <p>
     *  This method does only one thing.
     *   1. Checkout the repository files to the given location.
     *
     *   At the end of the execution all the project files should in the given path.
     *   Or the response should indicate the processing failed by setting the Response.success to false along with the
     *   error message.
     * </p>
     *
     * @param task
     *  <p>
     *      Contains Version-Control System connection information.
     *      e.g.
     *      url - contains the VC endpoint
     *      username - VC username/access-key
     *      password - VC password/secret-key
     *      branch - Repository branch defaults to master.
     *  </p>
     *
     * @param path
     *  <p>
     *      File location where to check-out the version-control project.
     *  </p>
     * @return
     *  <p>
     *      VCTaskResponse - Should only set these properties.
     *      1. success - true/false
     *      2. logs - execution or error logs.
     *  </p>
     */
    VCTaskResponse process(Task task, String path);

    String push(String path, String username, String password);
    String pushRemove(String path, String username, String password);
}

