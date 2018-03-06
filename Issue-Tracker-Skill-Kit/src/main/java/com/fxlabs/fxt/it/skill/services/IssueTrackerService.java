package com.fxlabs.fxt.it.skill.services;

import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;

/**
 * @author Mohammed Shoukath Ali
 */
public interface IssueTrackerService {

    /**
     * <p>
     *  This method does only one thing.
     *   1. Interacts with  the issue   tracker for managing issues.
     *
     *
     * </p>
     *
     * @param task
     *  <p>
     *      Contains Issue-Tracker System connection information.
     *      e.g.
     *      url - contains the issue tracker endpoint
     *      username - IssueTracker username/access-key
     *      password - IssueTracker password/secret-key
     *      project - name of the project under which issues to be created.
     *  </p>
     *
     * @return
     *  <p>
     *      VCTaskResponse - Should only set these properties.
     *      1. success - true/false
     *      2. logs - execution or error logs.
     *  </p>
     */
    ITTaskResponse process(TestCaseResponse task);
}

