package com.fxlabs.fxt.codegen.generators.utils;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 * @since 4/11/2018
 */

public class ParamUtil {
    //TODO add the possible pagination querry param names
    private static String[] skippedProps = {
            "page",
            "pageSize"
    };

    static List<String> skip = Arrays.asList(skippedProps);

    public static boolean isPaginationParam(String p) {

        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(p, "null")
                || org.apache.commons.lang3.StringUtils.isEmpty(p)) {
            return false;
        }

        if (skip.contains(p)) {
            return true;
        }
        return false;
    }
}
