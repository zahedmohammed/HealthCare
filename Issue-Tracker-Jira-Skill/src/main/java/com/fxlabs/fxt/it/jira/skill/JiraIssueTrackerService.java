package com.fxlabs.fxt.it.jira.skill;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
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
            URI uri = new URI(task.getIssueTrackerHost());

            final String JIRA_USERNAME = "luqmanshareef@gmail.com"; //get from task
            final String JIRA_PASSWORD = "luqssh123"; // get from task

            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, JIRA_USERNAME, JIRA_PASSWORD);

            if (task.getIssueId() == null){
                if (task.getResult().equalsIgnoreCase("FAIL")) {
                    // TODO: if it is a test rerun result and there is no issue created already for this test case
                    BasicIssue issue = createIssue(client, task);
                    response.setIssueId(issue.getKey());
                    System.out.println("Issue created........." + issue.getKey());
                }
            }else{
                if (task.getResult().equalsIgnoreCase("PASS")) {
//                    IssueRestClient issueClient = client.getIssueClient();
//                    BasicIssue issue = issueClient.getIssue(task.getIssueId()).claim();
                    updateIssue(client, task, task.getIssueId());
                }
            }
            response.setSuccess(true);
//            response.setLogs(taskLogger.get().toString());
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

    private BasicIssue createIssue(JiraRestClient client, TestCaseResponse task){
        IssueRestClient issueClient = client.getIssueClient();

        String projectKey = "LFP"; // get from task
        StringBuffer summary = new StringBuffer();
//        summary.append(task.getResponse()  );
        summary.append( task.getSuite() + " " + task.getResult()  );

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
        return basicIssue.claim();
    }

    private BasicIssue updateIssue(JiraRestClient client, TestCaseResponse task, String issueKey){

        IssueRestClient issueClient = client.getIssueClient();
        BasicIssue issue = issueClient.getIssue(issueKey).claim(); // get Key from task

        String projectKey = "";  //get from task

        new FieldInput(IssueFieldId.STATUS_FIELD, "{\\\"name\\\": \\\"Done\\\",\\\"id\\\": \\\"10001\\\",\\\"statusCategory\\\": {\\\"self\\\": \\\"https://luqmans.atlassian.net/rest/api/2/statuscategory/3\\\",\\\"id\\\": 3,\\\"key\\\": \\\"done\\\",\\\"colorName\\\": \\\"green\\\",\\\"name\\\": \\\"Done\\\"}\n");
        IssueInput issueInput = new IssueInputBuilder(projectKey, 10004L)
//				.setAssigneeName("admin")
                .setPriorityId(5L)
                .setFieldInput(new FieldInput(IssueFieldId.STATUS_FIELD, "done"))
//				.setReporterName("admin")
//                .setDescription(desc.toString())
                .build();

        issueClient.updateIssue(issueKey, issueInput).claim();

        return issue;

    }

}
