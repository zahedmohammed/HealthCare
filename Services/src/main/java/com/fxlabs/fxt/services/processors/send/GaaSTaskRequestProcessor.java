package com.fxlabs.fxt.services.processors.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GaaSTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    public void process() {

    }

}
