package com.fxlabs.issues.rest.base;

import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.exceptions.FxException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Aspect
@Component
public class ExceptionTranslator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("bean(*Controller)")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object retVal = pjp.proceed();
            return retVal;
        } catch (FxException e) {
            logger.warn(e.getLocalizedMessage());
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", e.getLocalizedMessage()));
        } catch (DataIntegrityViolationException e) {
            logger.warn(e.getLocalizedMessage());
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Resource name or key already exists."));
        } catch (IllegalArgumentException e) {
            logger.warn(e.getLocalizedMessage());
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", e.getLocalizedMessage()));
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", e.getLocalizedMessage()));
        }
    }
}
