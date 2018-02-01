package com.fxlabs.fxt.bot.assertions;

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
public class AssertionLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATE_FORMAT = "yyyy-MM-dd KK:mm:ss.SSS";
    final Logger logger = LoggerFactory.getLogger(getClass());
    private StringBuilder sb = new StringBuilder();

    public void append(LogType logType, String suite, String log) {

        FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT, TimeZone.getTimeZone("UTC"));

        String msg = String.format("%s %s [%s] : %s",
                fdf.format(new Date()),
                StringUtils.leftPad(logType.toString(), 6),
                StringUtils.leftPad(suite, 25),
                log);

        sb.append(msg).append("\n");
        //logger.warn(msg);
    }

    public String getLogs() {
        return sb.toString();
    }

    public enum LogType {
        INFO, ERROR, DEBUG
    }

}
