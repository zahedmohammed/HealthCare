package com.fxlabs.fxt.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    public final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String API_BASE = "/api/v1";
    public static final String USER_BASE = API_BASE + "/users";

    public final String PAGE_PARAM = "page";
    public final String PAGE_SIZE_PARAM = "pageSize";

    public final String DEFAULT_PAGE_VALUE = "0";
    public final String DEFAULT_PAGE_SIZE_VALUE = "20";


}
