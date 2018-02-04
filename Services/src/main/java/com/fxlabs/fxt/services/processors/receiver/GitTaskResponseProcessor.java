package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.git.GitTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GitTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    //AtomicInteger i = new AtomicInteger(1);
    public void process(GitTaskResponse task) {
        try {
            //logger.info("Response {}", i.incrementAndGet());
            logger.info("Task response [{}]...", task.getProjectId());
            // TODO - Replace this with job updating RunTask status

            //runRepository.save(run);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            //process(task);
        }
    }
}
