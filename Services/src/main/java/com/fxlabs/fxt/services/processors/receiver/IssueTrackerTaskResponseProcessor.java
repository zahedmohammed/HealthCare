package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseRepository;
import com.fxlabs.fxt.dto.alerts.*;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.services.alerts.AlertService;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class IssueTrackerTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestCaseResponseESRepository testCaseResponseESRepository;

    @Autowired
    private TestCaseResponseRepository testCaseResponseRepository;

    @Autowired
    private TestCaseResponseConverter converter;


    @Autowired
    private AlertService alertService;

    public void process(ITTaskResponse task) {
        try {
            logger.info("IssuerTracker Task response [{}]...", task.getProjectName());

            Optional<TestCaseResponse> optional = testCaseResponseESRepository.findById(task.getTestCaseResponseId());

            if (optional.get() != null) {
                TestCaseResponse response = optional.get();
                response.setIssueId(task.getIssueId());
                testCaseResponseRepository.save(response);
                testCaseResponseESRepository.save(response);

            }
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}
