package com.fxlabs.fxt.it.git.skill;

import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.it.skill.services.ITTask;
import com.fxlabs.fxt.it.skill.services.IssueTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 *
 * @author Intesar Mohammed
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class GitIssueTrackerService implements IssueTrackerService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

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
     *      Contains Issue-Tracker System connection information.
     *      e.g.
     *      url - contains the IssueTracker endpoint
     *      username - IssueTracker username/access-key
     *      password - IssueTracker password/secret-key
     *      branch - Repository branch defaults to master.
     *  </p>
     *
     *
     * @return
     *  <p>
     *      ITTaskResponse - Should only set these properties.
     *      1. success - true/false
     *      2. logs - execution or error logs.
     *  </p>
     */
    @Override
    public ITTaskResponse process(final ITTask task) {

        ITTaskResponse response = new ITTaskResponse();

        try {
           // TODO Create/Update bugs/issue in gitbub
//
//            Issue issue = new Issue();
//            issue.setTitle("Title_" + Math.random());
//            issue.setNumber(2);
//            issue.setState(IssueService.STATE_OPEN);
//
//            String body = "Body_" + Math.random();
//
//            issue.setBody(body);
//            List<Label> newLabels = new ArrayList<>();
//            //add tags to labels
//            Label label = new Label();
//            label.setName("Tag_" + Math.random());
//            newLabels.add(label);
//
//            issue.setLabels(newLabels);
//            //creates an issue remotely
//
//            GitHubClient client = new GitHubClient();
//            client.setOAuth2Token("3cdd07af86c6098a0ddef55b684d46d291e44f1a");
//
//            IssueService issueService = new IssueService(client);
//
//            issueService.getClient().setOAuth2Token("3cdd07af86c6098a0ddef55b684d46d291e44f1a");
//
//                issue = issueService.editIssue("shoukathmd", "My_first_repo", issue);
//
//            System.out.print(issue);
//            //comment.setIssueNumber(issue.getNumber());

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            //response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return response;

    }

}
