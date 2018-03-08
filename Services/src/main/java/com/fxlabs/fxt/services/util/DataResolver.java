package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.services.vault.VaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataResolver {

    final static String PATTERN = "\\{\\{@Vault.(.*?)\\}\\}";
    final Logger logger = LoggerFactory.getLogger(getClass());

    private VaultService vaultService;

    public DataResolver(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    public String resolve(String data) {
        if (StringUtils.isEmpty(data)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        String response = null;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(data);
        List<String> patterns = new ArrayList<>();
        // check all occurrence
        while (matcher.find()) {
            String value = matcher.group(1);
            patterns.add(value);
        }

        response = data;

        for (String key : patterns) {
            // send FxLabs/Key1 to VaultService
            String val = vaultService.findByName(key).getData();
            response = StringUtils.replace(response, "{{@Vault." + key + "}}", val);
        }

        logger.debug("Data [{}] response [{}]", data, response);
        return response;

    }
}