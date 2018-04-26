package com.fxlabs.fxt.codegen.code;

import org.apache.commons.lang3.BooleanUtils;
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

    private boolean print;

    public BotLogger() {
    }

    public BotLogger(boolean print) {
        this.print = print;
    }

    public void append(LogType logType, String suite, String log) {

        try {
            FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT, TimeZone.getTimeZone("UTC"));

            String msg = String.format("%s %s [%s] : %s",
                    StringUtils.leftPad(fdf.format(new Date()), 20),
                    StringUtils.leftPad(logType.toString(), 6),
                    StringUtils.leftPad(suite, 40, "."),
                    log);

            sb.append(msg).append("\n");
            if (BooleanUtils.isTrue(print)) {
                System.out.println(msg);
            }
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
