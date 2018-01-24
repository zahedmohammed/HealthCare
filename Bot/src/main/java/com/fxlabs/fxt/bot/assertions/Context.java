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

    private String suitename;
    // init & cleanup
    private Map<String, String> data = new HashMap<>();
    private Map<String, HttpHeaders> headerData = new HashMap<>();

    // suite
    private String request;
    private String response;
    private String statusCode;
    private HttpHeaders headers;

    private Stack<BotTask> initTasks = new Stack();

    private AssertionLogger logs;

    // status - pass or fail
    private String result = "";

    private Context parent;

    public Context(Context parent) {
        this.parent = parent;
    }

    public Context(String suitename, AssertionLogger logs) {
        this.suitename = suitename;
        this.logs = logs;
    }

    public Context withSuiteData(String request, String response, String statusCode, HttpHeaders headers) {
        this.request = request;
        this.response = response;
        this.statusCode = statusCode;
        this.headers = headers;
        if (parent != null) {
            this.parent.request = request;
            this.parent.response = response;
            this.parent.statusCode = statusCode;
            this.parent.headers = headers;
        }
        return this;
    }

    public Context withRequest(String name, String request) {
        this.data.put(name, request);
        if (parent != null) {
            parent.withRequest(name, request);
        }
        return this;
    }

    public Context withResponse(String name, String request) {
        this.data.put(name, response);
        if (parent != null) {
            parent.withResponse(name, request);
        }
        return this;
    }

    public Context withHeaders(String name, HttpHeaders headers) {
        this.headerData.put(name, headers);
        if (parent != null) {
            parent.withHeaders(name, headers);
        }
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

    public HttpHeaders getHeaders(String suite) {
        if (StringUtils.isEmpty(suite)) {
            return headers;
        } else {
            return this.headerData.get(suite + "_Headers");
        }
    }

    public AssertionLogger getLogs() {
        if (parent != null) {
            return parent.logs;
        }
        return logs;
    }

    public String getResult() {
        if (parent != null) {
            return this.parent.result;
        }
        return result;
    }

    public String get(String key) {
        return data.get(key);
    }

    public Stack<BotTask> getInitTasks() {
        return this.initTasks;
    }

    public void setResult(String result) {
        if (parent != null) {
            if (StringUtils.equalsIgnoreCase(this.parent.result, "fail")
                    || StringUtils.equalsIgnoreCase(this.parent.result, "skip")
                    || StringUtils.isEmpty(result)) {

            } else {
                this.parent.result = result;
            }
        } else {
            if (StringUtils.equalsIgnoreCase(this.result, "fail")
                    || StringUtils.equalsIgnoreCase(this.result, "skip")
                    || StringUtils.isEmpty(result)) {

            } else {
                this.result = result;
            }
        }
    }

    public String getSuitename() {
        if (parent != null) {
            return this.parent.suitename;
        } else {
            return suitename;
        }
    }
}
