package com.fxlabs.fxt.bot.assertions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.List;

public class AssertionContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private String request;
    private String response;
    private String statusCode;
    private HttpHeaders headers;
    private StringBuilder logs;
    private String result = "";

    public AssertionContext(String request, String response, String statusCode, HttpHeaders headers, StringBuilder logs) {
        this.request = request;
        this.response = response;
        this.statusCode = statusCode;
        this.headers = headers;
        this.logs = logs;
    }


    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public StringBuilder getLogs() {
        return logs;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        System.out.println (result);
        if (StringUtils.equalsIgnoreCase(this.result, "fail")
                || StringUtils.equalsIgnoreCase(this.result, "skip")
                || StringUtils.isEmpty(result)) {

        } else {
            this.result = result;
        }
    }
}
