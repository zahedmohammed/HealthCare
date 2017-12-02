package com.fxlabs.fxt.bot.assertions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

public class AssertionLogger implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringBuilder sb = new StringBuilder();

    public void append(String log) {
        sb.append(log).append("\n");
    }

    public String getLogs() {
        return sb.toString();
    }

}
