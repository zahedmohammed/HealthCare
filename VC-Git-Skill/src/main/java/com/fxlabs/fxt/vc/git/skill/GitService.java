package com.fxlabs.fxt.vc.git.skill;

import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.vc.git.skill.services.Task;
import com.fxlabs.fxt.vc.git.skill.services.VersionControlService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;

@Component
public class GitService implements VersionControlService {

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

            response.setPath(path);

            Repository repository = findAndCreateRepository(task, response, path);

            if (repository == null) {
                response.setLogs(taskLogger.get().toString());
                logger.info(response.toString());
                return response;
            }

            if (!isRepoClean(repository)) {
                deleteRepo(path);
                repository = findAndCreateRepository(task, response, path);
            }

            if (!pull(repository, task.getVcUsername(), task.getVcPassword())) {
                response.setLogs(taskLogger.get().toString());
                logger.info(response.toString());
                return response;
            }

            String commitId = head(repository);
            response.setVcLastCommit(commitId);

            response.setSuccess(true);
            response.setLogs(taskLogger.get().toString());
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

    public boolean deleteRepo(String path) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
            return true;
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }
        return false;
    }

    private Repository findAndCreateRepository(Task task, VCTaskResponse response, String path) {
        Repository repository = findRepository(path);

        if (repository == null) {
            deleteRepo(path);
            repository = createRepository(task.getVcUrl(), task.getVcUsername(), task.getVcPassword(), task.getVcBranch(), path);
        }

        return repository;
    }

    private Repository findRepository(String path) {
        Repository repository = null;

        try {

            repository = Git.open(new File(path + "/.git")).getRepository();

        } catch (RepositoryNotFoundException rnf) {
            logger.warn(rnf.getLocalizedMessage());
        } catch (IOException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return repository;
    }

    private Repository createRepository(String url, String username, String password, String branch, String path) {
        Repository repository = null;
        try {

            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(new File(path));

            if (StringUtils.isNotEmpty(branch)) {
                cloneCommand.setBranch(branch);
            }

            if (StringUtils.isNotEmpty(username) && StringUtils.isNoneEmpty(password)) {
                cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            }

            Git git = cloneCommand.call();

            repository = git.getRepository();

        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return repository;
    }

    private boolean isRepoClean(Repository repository) {
        try {
            return new Git(repository).status().call().isClean();
        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }
        return false;
    }

    private boolean pull(Repository repository, String username, String password) {
        try {
            PullCommand pullCommand = new Git(repository).pull();
            if (StringUtils.isNotEmpty(username)) {
                pullCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            }
            return pullCommand.call().isSuccessful();
        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return false;
    }

    @Override
    public String push(String path, String username, String password) {
        try {

            taskLogger.get().append("Pushing changes").append("\n");
            logger.info("Pushing changes");
            Git git = Git.open(new File(path));

            if(!isUnStagedFiles(git)) {
                taskLogger.get().append("No un-staged files found.").append("\n");
                logger.info("No un-staged files found.");
                return "No un-staged files found.";
            }
            // add
            // commit
            // push
            addAndCommit(git, "Fx Bot commit", ".");

            PushCommand pushCommand = git.push();
            if (StringUtils.isNotEmpty(username)) {
                pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            }
            pushCommand.call();
            taskLogger.get().append("Push successful!").append("\n");
            logger.info("Push successful!");
            // TODO hand response
        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }

        return taskLogger.get().toString();
    }

    private boolean isUnStagedFiles(Git git) {
        Status status = null;
        try {
            status = git.status().call();
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
            return false;
        }

        if (!CollectionUtils.isEmpty(status.getConflicting())) {
            taskLogger.get().append("Conflicting Git files found.").append("\n");
            return false;
        }

        if (!CollectionUtils.isEmpty(status.getUntracked())) {
            return true;
        }

        if (!CollectionUtils.isEmpty(status.getModified())) {
            return true;
        }


        if (!CollectionUtils.isEmpty(status.getMissing())) {
            return true;
        }

        return false;
    }

    private void addAndCommit(Git git, String message, String pathToAdd) {
        add(git, pathToAdd);
        commit(git, message);
    }

    private boolean commit(Git git, String message) {
        CommitCommand commit = git.commit();
        try {
            commit.setMessage(message).call();
            return true;
        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
            return false;
        }
    }

    private boolean add(Git git, String pathToAdd) {
        AddCommand add = git.add();
        try {
            add.addFilepattern(pathToAdd).call();
            return true;
        } catch (GitAPIException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
            return false;
        }
    }

    private String head(Repository repository) {
        try {
            ObjectId lastCommitId = repository.resolve(Constants.HEAD);
            return lastCommitId.toString();
        } catch (IOException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            taskLogger.get().append(ex.getLocalizedMessage()).append("\n");
        }
        return null;
    }
}
