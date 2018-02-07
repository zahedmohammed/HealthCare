package com.fxlabs.fxt.gaas.services;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.git.GitTaskResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class GitService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();

    public GitTaskResponse process(GitTask task) {

        GitTaskResponse response = new GitTaskResponse();
        response.setProjectId(task.getProjectId());
        response.setSuccess(false);
        String path = null;

        try {
            taskLogger.set(new StringBuilder());


            path = "/var/lib/fx/" + RandomStringUtils.randomAlphabetic(6);//task.getProjectId();
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

            if (!pull(repository, task.getGitUsername(), task.getGitPassword())) {
                response.setLogs(taskLogger.get().toString());
                logger.info(response.toString());
                return response;
            }

            String commitId = head(repository);
            response.setGitLastCommit(commitId);

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

    private Repository findAndCreateRepository(GitTask task, GitTaskResponse response, String path) {
        Repository repository = findRepository(path);

        if (repository == null) {
            deleteRepo(path);
            repository = createRepository(task.getGitUrl(), task.getGitUsername(), task.getGitPassword(), task.getGitBranch(), path);
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

            if (StringUtils.isNotEmpty(username)) {
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
