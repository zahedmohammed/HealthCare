package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionTranslator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("bean(*Controller)")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object retVal = pjp.proceed();
            return retVal;
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", e.getLocalizedMessage()));
        }
    }
}
