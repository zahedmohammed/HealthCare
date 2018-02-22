package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dto.run.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class SuiteResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final SuiteESRepository suiteESRepository;
    private final SuiteConverter suiteConverter;

    @Autowired
    public SuiteResponseProcessor(SuiteESRepository suiteESRepository, SuiteConverter suiteConverter) {
        this.suiteESRepository = suiteESRepository;
        this.suiteConverter = suiteConverter;
    }

    public void process(Suite suite) {
        try {
            logger.info("Suite response [{}]...", suite.getSuiteName());
            suiteESRepository.save(suiteConverter.convertToEntity(suite));

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}
