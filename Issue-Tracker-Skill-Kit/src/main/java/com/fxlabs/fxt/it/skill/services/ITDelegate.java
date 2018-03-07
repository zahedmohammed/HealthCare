package com.fxlabs.fxt.it.skill.services;

import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.it.skill.amqp.Sender;
import com.fxlabs.fxt.sdk.services.FxCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ITDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private IssueTrackerService issueTrackerService;

    @Autowired
    private FxCommandService service;

    public void process(TestCaseResponse task) {
        System.out.print("In IT DELEGATE");

        issueTrackerService.process(task);
    }


}
