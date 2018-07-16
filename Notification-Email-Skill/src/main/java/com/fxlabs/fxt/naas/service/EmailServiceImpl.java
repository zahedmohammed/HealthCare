package com.fxlabs.fxt.naas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EmailServiceImpl implements EmailService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void send(String subject, String text, String... tos) {
        try {
            for (String to : tos) {
                String[] tokensChannel = org.apache.commons.lang3.StringUtils.split(to, ",");
                List<String> tos_ = Arrays.asList(tokensChannel);
                for (String to_ : tos_) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(to_);
                    message.setSubject(subject);
                    message.setText(text);
                    //message.setFrom("FX Labs, Inc");
                    emailSender.send(message);
                }
                logger.info("to [{}] subject [{}]", tos, subject);
            }

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}