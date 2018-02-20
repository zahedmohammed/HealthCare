package com.fxlabs.fxt.services.project;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

public class CronUtils {

    public static boolean isValidCron(String cron) {
        if (!org.springframework.util.StringUtils.isEmpty(cron) && CronSequenceGenerator.isValidExpression(cron)) {
            return true;
        }
        return false;
    }
    public static Date cronNext(String cron) {
        if (!org.springframework.util.StringUtils.isEmpty(cron) && CronSequenceGenerator.isValidExpression(cron)) {
            CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
            Date start = DateUtils.addMinutes(new Date(), 2);
            return cronSequenceGenerator.next(start);
        }
        return new Date();
    }

}
