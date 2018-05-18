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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.it.skill.services.IssueTrackerService;
import com.fxlabs.fxt.it.skill.services.ITTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class JiraIssueTrackerService implements IssueTrackerService {

    final Logger logger = LoggerFactory.getLogger(getClass());
    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();
    private String fxIssueTrackerBot;
    private String fxIssueTrackerBotSecretKey;

    private final Long ISSUE_TYPE_ID_BUG = 10005L;

    @Autowired
    public JiraIssueTrackerService(@Value("${FX_ISSUE_TRACKER_BOT}")String fxIssueTrackerBot, @Value("${FX_ISSUE_TRACKER_BOT_SECRETKEY}") String fxIssueTrackerBotSecretKey) {
        this.fxIssueTrackerBot = fxIssueTrackerBot;
        this.fxIssueTrackerBotSecretKey = fxIssueTrackerBotSecretKey;
    }

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
           // Create/Update bugs/issue in issue tracker JIRA
            String url = task.getEndpoint();
            URI uri = new URI(task.getIssueTrackerHost());

            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//            JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, fxIssueTrackerBot, fxIssueTrackerBotSecretKey);
            JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, task.getUsername(), task.getPassword());

            BasicIssue issue = null;
            if (StringUtils.isEmpty(task.getIssueId())){
                issue = createIssue(client, task);
                response.setIssueId(issue.getKey());
            }else{
                issue = updateIssue(client, task);
            }
            response.setSuccess(true);
//            response.setLogs(taskLogger.get().toString());
            response.setSuccess(true);
//            response.setLogs(taskLogger.get().toString());
            response.setTestCaseResponseId(task.getId());
            response.setIssueId(String.valueOf(issue.getId()));
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

        StringBuffer summary = new StringBuffer();
        summary.append(task.getSuite());
        String projectKey = task.getIssueTrackerProjectName(); //"lFP";//

        StringBuffer desc = new StringBuffer();
        desc.append("Request: \n" + task.getRequestEval());
        desc.append("\n");
        desc.append("Response: \n" + task.getResponse());

        IssueInput newIssue = new IssueInputBuilder(projectKey, ISSUE_TYPE_ID_BUG , summary.toString())
//				.setAssigneeName("admin")
                .setPriorityId(5L)
//				.setReporterName("admin")
//                .setFieldInput(new FieldInput(IssueFieldId.STATUS_FIELD, "To Do"))
                .setDescription(desc.toString())
                .build();
        Promise<BasicIssue> basicIssue = issueClient.createIssue(newIssue);
        System.out.println("Issue created.......... " + basicIssue.claim().getKey());
        return basicIssue.claim();
    }

    private BasicIssue updateIssue(JiraRestClient client, TestCaseResponse task){

        IssueRestClient issueClient = client.getIssueClient();
        BasicIssue issue = issueClient.getIssue(task.getIssueId()).claim();

        StringBuffer desc = new StringBuffer();
        desc.append("Rerun results :\n");
        desc.append("Request: \n" + task.getRequest());
        desc.append("\n");
        desc.append("Response: \n" + task.getResponse());
//        issueClient.a

        String projectKey = task.getIssueTrackerProjectName();
        IssueInput issueInput = new IssueInputBuilder(projectKey, ISSUE_TYPE_ID_BUG )
//				.setAssigneeName("admin")
                .setPriorityId(5L)
//                .setFieldInput(new FieldInput(IssueFieldId.STATUS_FIELD, "done"))
//				.setReporterName("admin")
                .setDescription(desc.toString())
                .build();
//        Ierator itr = issueInput.getFields().keySet().iterator()

        issueClient.updateIssue(task.getIssueId(), issueInput).claim();

        System.out.println("Issue updated.......... " + issue.getId());
        return issue;
    }

}
