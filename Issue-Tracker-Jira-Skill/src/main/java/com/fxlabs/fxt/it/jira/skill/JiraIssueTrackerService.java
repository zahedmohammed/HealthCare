package com.fxlabs.fxt.it.jira.skill;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.it.skill.services.IssueTrackerService;
import com.fxlabs.fxt.it.skill.services.ITTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class JiraIssueTrackerService implements IssueTrackerService {

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
    public ITTaskResponse process(final TestCaseResponse task) {
        ITTaskResponse response = new ITTaskResponse();
        try {
           // TODO Create/Update bugs/ussue in issue tracker JIRA

            String url = task.getEndpoint();
            URI uri = new URI(url);

            final String JIRA_USERNAME = ""; //get from task
            final String JIRA_PASSWORD = ""; // get from task

            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, JIRA_USERNAME, JIRA_PASSWORD);

            if (task.getResult().equals("FAIL")) {
                // TODO: if it is a test rerun result and there is no issue created already for this test case
                BasicIssue issue = createIssue(client, task);
                response.setIssueId(issue.getKey());
            }else{
                // TODO: if there was already an issue, and the TC passed now, update the issue
                // Find the corresponding issue
//                updateIssue(client, task, issue);
            }
            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());
            return response;
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            //response.setLogs(taskLogger.get().toString());
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return response;

    }

//    public JiraRestClient init(){
//
//        final String JIRA_URL = "https://luqmans.atlassian.net";
//        final String JIRA_ADMIN_USERNAME = "luqmanshareef@gmail.com";
//        final String JIRA_ADMIN_PASSWORD = "luqssh123";
//        JiraRestClient client = null;
//
//        URI uri;
//        try {
//            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//            uri = new URI(JIRA_URL);
//            client = factory.createWithBasicHttpAuthentication(uri, JIRA_ADMIN_USERNAME, JIRA_ADMIN_PASSWORD);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return client;
//    }

    private BasicIssue createIssue(JiraRestClient client, TestCaseResponse task){
        IssueRestClient issueClient = client.getIssueClient();

        String projectKey = "LFP"; // get from task
        StringBuffer summary = new StringBuffer();
        summary.append(task.getResponse()  );
        summary.append(task.getResult()  );

        StringBuffer desc = new StringBuffer();
        desc.append(task.getTestCase());
        desc.append(task.getResult());

        IssueInput newIssue = new IssueInputBuilder(projectKey, 10004L, summary.toString())
//				.setAssigneeName("admin")
                .setPriorityId(5L)
//				.setReporterName("admin")
                .setDescription(desc.toString())
                .build();
        Promise<BasicIssue> basicIssue = issueClient.createIssue(newIssue);
        System.out.println(basicIssue.claim().getKey() + " created.");
        return basicIssue.claim();
    }

    private BasicIssue updateIssue(JiraRestClient client, TestCaseResponse task, String issueKey){

        IssueRestClient issueClient = client.getIssueClient();
        BasicIssue issue = issueClient.getIssue(issueKey).claim(); // get Key from task

        String projectKey = "";  //get from task

        IssueInput issueInput = new IssueInputBuilder(projectKey, 10004L)
//				.setAssigneeName("admin")
                .setPriorityId(5L)
//				.setReporterName("admin")
//                .setDescription(desc.toString())
                .build();

        issueClient.updateIssue(issueKey, issueInput).claim();

        return issue;

    }

}
