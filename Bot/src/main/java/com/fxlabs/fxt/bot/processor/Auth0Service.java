package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.dto.project.Auth;

public interface Auth0Service {

    public String getAccessTokenForClientCredentials(String tokenURI, String clientId, String clientSecret, String audience);
}
