package com.fxlabs.fxt.bot.processor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Override
    @Cacheable(value = "auth0AccessTokenCache", key = "#tokenURI + #clientId + #audience + #username + #password + #scope", sync = true)
    public String getAccessTokenForPassword(String tokenURI, String clientId, String audience, String username, String password, String scope) {

        RestTemplate restTemplate = new RestTemplate(HttpClientFactoryUtil.getInstance());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.set("Accept", "application/json");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("audience", audience);
        formData.add("grant_type", "password");
        formData.add("username", username);
        formData.add("password", password);
        formData.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(formData, httpHeaders);

        ResponseEntity<AccessToken> response = null;

        try {
            response = restTemplate.exchange(tokenURI, HttpMethod.POST, requestEntity, AccessToken.class);

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
