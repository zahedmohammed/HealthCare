package com.fxlabs.fxt.bot.assertions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

public class AssertionLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    final Logger logger = LoggerFactory.getLogger(getClass());

    private StringBuilder sb = new StringBuilder();

    public void append(String log) {
        sb.append(log).append("\n");
        logger.warn(log);
    }

    public String getLogs() {
        return sb.toString();
    }

}
