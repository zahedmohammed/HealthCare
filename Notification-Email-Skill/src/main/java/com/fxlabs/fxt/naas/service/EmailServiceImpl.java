package com.fxlabs.fxt.naas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void send(String subject, String text, String... tos) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(tos);
            message.setSubject(subject);
            message.setText(text);
            //message.setFrom("FX Labs, Inc");
            emailSender.send(message);
            logger.info("to [{}] subject [{}]", tos, subject);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}