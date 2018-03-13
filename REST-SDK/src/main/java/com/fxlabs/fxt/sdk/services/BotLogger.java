package com.fxlabs.fxt.sdk.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Intesar Shannan Mohammed
 */
public class BotLogger implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATE_FORMAT = "yyyy-MM-dd KK:mm:ss";
    final Logger logger = LoggerFactory.getLogger(getClass());
    private StringBuilder sb = new StringBuilder();

    public void append(LogType logType, String suite, String log) {

        try {
            FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT, TimeZone.getTimeZone("UTC"));

            String msg = String.format("%s %s [%s] : %s",
                    StringUtils.leftPad(fdf.format(new Date()), 20),
                    StringUtils.leftPad(logType.toString(), 6),
                    StringUtils.leftPad(suite, 40, "."),
                    log);

            sb.append(msg).append("\n");
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage());
        }
        //logger.warn(msg);
    }

    public String getLogs() {
        return sb.toString();
    }

    public enum LogType {
        INFO, ERROR, DEBUG, WARN
    }

}
