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
    private AssertionLogger.LogType logType = AssertionLogger.LogType.ERROR;
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

    public Context(String suitename, AssertionLogger logs, String logType) {
        this.suitename = suitename;
        this.logs = logs;
        if (StringUtils.equalsIgnoreCase(logType, AssertionLogger.LogType.DEBUG.toString())) {
            this.logType = AssertionLogger.LogType.DEBUG;
        }
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

        String suite = this.suitename;
        if (StringUtils.isEmpty(this.suitename) && this.parent != null) {
            suite = this.parent.suitename;
        }

        // log
        this.log(suite, String.format("Request [%s]", request));
        this.log(suite, String.format("Response [%s]", response));
        this.log(suite, String.format("Headers [%s]", headers.toString()));
        this.log(suite, String.format("StatusCode [%s]", statusCode));

        return this;
    }

    public Context withRequest(String name, String request) {
        this.data.put(name, request);
        // log
        this.log(name, String.format("Request [%s]", request));

        if (parent != null) {
            parent.withRequest(name, request);
        }
        return this;
    }

    public Context withResponse(String name, String response) {
        this.data.put(name, response);
        // log
        this.log(name, String.format("Response [%s]", response));

        if (parent != null) {
            parent.withResponse(name, response);
        }
        return this;
    }

    public Context withHeaders(String name, HttpHeaders headers) {
        this.headerData.put(name, headers);
        // log
        this.log(name, String.format("Headers [%s]", headers.toString()));

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

    private void log(String suite, String msg) {
        if ((this.parent != null && this.parent.logType == AssertionLogger.LogType.DEBUG) ||
                this.logType == AssertionLogger.LogType.DEBUG) {

            if (this.parent != null) {
                this.parent.getLogs().append(this.parent.logType, suite, msg);
            } else {
                this.getLogs().append(this.logType, suite, msg);
            }
        }
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

    public void setResult(String result) {
        if (parent != null) {
            if (StringUtils.equalsIgnoreCase(this.parent.result, "fail")
                    || StringUtils.equalsIgnoreCase(this.parent.result, "skip")
                    || StringUtils.isEmpty(result)) {

            } else {
                this.parent.result = result;
                // log
                this.log(this.parent.suitename, String.format("Result [%s]", result));
            }
        } else {
            if (StringUtils.equalsIgnoreCase(this.result, "fail")
                    || StringUtils.equalsIgnoreCase(this.result, "skip")
                    || StringUtils.isEmpty(result)) {

            } else {
                this.result = result;
                // log
                this.log(this.suitename, String.format("Result [%s]", result));
            }
        }
    }

    public String get(String key) {
        return data.get(key);
    }

    public Stack<BotTask> getInitTasks() {
        return this.initTasks;
    }

    public String getSuitename() {
        if (parent != null) {
            return this.parent.suitename;
        } else {
            return suitename;
        }
    }
}
