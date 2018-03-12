package com.fxlabs.fxt.it.git.skill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.it.skill.amqp.Sender;
import com.fxlabs.fxt.it.skill.services.ITTask;
import com.fxlabs.fxt.it.skill.services.IssueTrackerService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Intesar Mohammed
 * @author Mohammed Shoukath Ali
 *
 *
 */

@Component
public class GitIssueTrackerService implements IssueTrackerService {

    public static final String COLON = "  :  ";
    public static final String LINE_SEPERATOR = "\n";

    /**
     * Issue open state filter value
     */
    public static final String STATE_OPEN = "open"; //$NON-NLS-1$

    /**
     * Issue closed state filter value
     */
    public static final String STATE_CLOSED = "closed"; //$NON-NLS-1$

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
        logger.info("In IT GitIssueTrackerService for project [{}]" , task.getProject());

    ITTaskResponse response = new ITTaskResponse();

        try {

            taskLogger.set(new StringBuilder());
            //TODO Create/Update bugs/issue in gitbub

            RepositoryId repositoryId = RepositoryId.createFromUrl(task.getIssueTrackerHost());

            if (repositoryId == null) {
                response.setLogs(taskLogger.get().toString());
                logger.info(response.toString());
                return response;
            }

            Issue issue = buildIssue(task);

            //creates an issue remotely

           // IssueService issueService = getIssueService("4bba0ed42c510710209e16a37321d30e64e5f4cd");
            IssueService issueService = getIssueService(task.getUsername(), task.getPassword());
            if(StringUtils.isNotEmpty(task.getIssueId())){
                //TODO update issue

                issue = editIssue(issue, issueService, repositoryId, task);

            } else {
                issue = createIssue(issue, issueService, repositoryId);
            }

           // response.setIssueId(issue.getNumber());

            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());
            response.setTestCaseResponseId(task.getId());
            response.setIssueId(String.valueOf(issue.getNumber()));

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

    private Issue buildIssue(TestCaseResponse task) {
        Issue issue = new Issue();

        //Title required
        issue.setTitle(task.getProject() + " : " +task.getSuite());

        issue.setState(IssueService.STATE_OPEN);

        StringBuilder sb = new StringBuilder();
        sb.append("Project").append(COLON).append(task.getProject()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Job").append(COLON).append(task.getJob()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Env").append(COLON).append(task.getEnv()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Region").append(COLON).append(task.getRegion()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Result").append(COLON).append(task.getResult()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Status Code").append(COLON).append(task.getStatusCode()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Headers").append(COLON).append(task.getHeaders()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Endpoint").append(COLON).append(task.getEndpointEval()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Request").append(COLON).append(LINE_SEPERATOR).append(task.getRequestEval()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Response").append(COLON).append(LINE_SEPERATOR).append(task.getResponse()).append(LINE_SEPERATOR).append(LINE_SEPERATOR)
                .append("Logs").append(COLON).append(LINE_SEPERATOR).append(task.getLogs()).append(LINE_SEPERATOR);
        String body = sb.toString();

        issue.setBody(body);

        List<Label> newLabels = new ArrayList<>();
        //add tags to labels
        Label label = new Label();
        label.setName(task.getProject());

        newLabels.add(label);
        issue.setLabels(newLabels);

        return issue;
    }

    private Issue createIssue(Issue issue, IssueService issueService, RepositoryId repositoryId) throws IOException {
        issue = issueService.createIssue(repositoryId, issue);
        return issue;
    }

    private Issue editIssue(Issue issue, IssueService issueService, RepositoryId repositoryId, TestCaseResponse task) throws IOException {
        if (StringUtils.equals(task.getResult(), "pass")) {
            issue.setState(STATE_CLOSED);
        } else {
            issue.setState(STATE_OPEN);
        }
        issue.setNumber(Integer.parseInt(task.getIssueId()));
        issue = issueService.editIssue(repositoryId, issue);

        return issue;
    }

    private IssueService getIssueService(String username, String password) {

        GitHubClient client = new GitHubClient();
        IssueService issueService = null;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            client.setCredentials(username, password);
            issueService = new IssueService(client);
        }

        if (issueService == null && StringUtils.isEmpty(username) && StringUtils.isNotEmpty(password)) {
            issueService = new IssueService();
            issueService.getClient().setOAuth2Token(password);
        }

        if (issueService == null) {
            issueService = getIssueService("mdshannan@gmail.com", "Abbh12#$");
        }

        return issueService;
    }

}
