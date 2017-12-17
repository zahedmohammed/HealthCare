package com.fxlabs.fxt.bot.assertions;

import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context implements Serializable {
    private static final long serialVersionUID = 1L;

    // init & cleanup
    private Map<String, String> data = new HashMap<>();

    // suite
    private String request;
    private String response;
    private String statusCode;
    private HttpHeaders headers;

    private Stack<BotTask> initTasks = new Stack();

    private AssertionLogger logs;

    // status - pass or fail
    private String result = "";

    public Context(AssertionLogger logs) {
        this.logs = logs;
    }

    public Context(String request, String response, String statusCode, HttpHeaders headers, AssertionLogger logs) {
        this.request = request;
        this.response = response;
        this.statusCode = statusCode;
        this.headers = headers;
        this.logs = logs;
    }

    public Context withSuiteData(String request, String response, String statusCode, HttpHeaders headers) {
        this.request = request;
        this.response = response;
        this.statusCode = statusCode;
        this.headers = headers;
        return this;
    }

    public Context withRequest(String name, String request) {
        this.data.put(name, request);
        return this;
    }

    public Context withResponse(String name, String request) {
        this.data.put(name, response);
        return this;
    }

    public Context withTask(BotTask task) {
        // add clean-up tasks to Stack
        if (task != null) {
            this.initTasks.push(task);
        }
        return this;
    }

    public String getRequest(String suite) {
        if (StringUtils.isEmpty(suite)) {
            return request;
        } else {
            return this.data.get(suite + "_Request");
        }
    }

    public String getResponse(String suite) {
        if (StringUtils.isEmpty(suite)) {
            return response;
        } else {
            return this.data.get(suite + "_Response");
        }
    }

    public String getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public AssertionLogger getLogs() {
        return logs;
    }

    public String getResult() {
        return result;
    }

    public String get(String key) {
        return data.get(key);
    }

    public Stack<BotTask> getInitTasks() {
        return this.initTasks;
    }

    public void setResult(String result) {
        if (StringUtils.equalsIgnoreCase(this.result, "fail")
                || StringUtils.equalsIgnoreCase(this.result, "skip")
                || StringUtils.isEmpty(result)) {

        } else {
            this.result = result;
        }
    }
}
