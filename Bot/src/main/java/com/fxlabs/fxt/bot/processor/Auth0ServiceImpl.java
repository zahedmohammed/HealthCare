package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.dto.project.Auth;
import com.fxlabs.fxt.dto.project.AuthType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth0ServiceImpl implements Auth0Service {

    @Override
    @Cacheable(value = "auth0AccessTokenCache", key = "#tokenURI + #clientId + #clientSecret + #audience", sync = true)
    public String getAccessTokenForClientCredentials(String tokenURI, String clientId, String clientSecret, String audience) {

        RestTemplate restTemplate = new RestTemplate(HttpClientFactoryUtil.getInstance());

        String req = String.format("{\"client_id\":\"%s\", \"client_secret\":\"%s\", \"audience\":\"%s\", \"grant_type\":\"client_credentials\"}", clientId, clientSecret, audience);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        //logger.info("Request: [{}]", req);
        HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

        ResponseEntity<AccessToken> response = null;

        try {
            response = restTemplate.exchange(tokenURI, HttpMethod.POST, request, AccessToken.class);

            if (response != null && response.getBody() != null) {
                String token = response.getBody().getAccess_token();
                return token;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Error retrieving access token from Auth0");
    }
}
