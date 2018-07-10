package com.fxlabs.fxt.naas.service;

import java.util.List;

public interface EmailService {
    public void send(String subject, String text, String... tos);
}
