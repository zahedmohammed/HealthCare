package com.fxlabs.fxt.codegen.generators;

import org.springframework.util.StringUtils;

import java.util.StringJoiner;

public class NameUtil {
    public static String extract(String path) {
        String[] tokens = path.split("/");
        StringJoiner joiner = new StringJoiner("_");
        for (String token : tokens) {
            if (StringUtils.isEmpty(token)) {
                continue;
            }
            //System.out.println("token : " + token);
            if (org.apache.commons.lang3.StringUtils.containsAny(token, "{")) {
                token = token.replace("{", "");
            }
            if (org.apache.commons.lang3.StringUtils.containsAny(token, "}")) {
                token = token.replace("}", "");
            }
            //System.out.println("Eval token : " + token);
            joiner.add(token.toLowerCase());
        }

        return joiner.toString();
    }
}
