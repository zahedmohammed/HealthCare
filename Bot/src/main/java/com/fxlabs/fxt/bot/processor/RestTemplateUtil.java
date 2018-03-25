package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.dto.project.Auth;
import com.fxlabs.fxt.dto.project.AuthType;
import com.fxlabs.fxt.dto.project.GrantType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.client.token.grant.redirect.AbstractRedirectResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class RestTemplateUtil {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ResponseEntity<String> execRequest(String url, HttpMethod method, HttpHeaders httpHeaders, String req, Auth auth) {

        // TODO - handle other auth types
        if (auth == null || auth.getAuthType() == null) {
            return execBasicRequest(url, method, httpHeaders, req, auth);
        } else if (auth.getAuthType() == AuthType.BASIC || auth.getAuthType() == AuthType.BasicAuth) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(auth.getUsername(), auth.getPassword()));
            return execBasicRequest(url, method, httpHeaders, req, auth);
        } else if (auth.getAuthType() == AuthType.OAuth_2_0) {
            return execOAuth2Request(url, method, httpHeaders, req, auth);
        } else {
            logger.warn("Invalid auth-type [{}]", auth.getAuthType());
        }

        return null;

    }


    private ResponseEntity<String> execOAuth2Request(String url, HttpMethod method, HttpHeaders httpHeaders, String req, Auth auth) {
        // execute request
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails(auth), clientContext);

        //logger.info("Request: [{}]", req);
        HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

        ResponseEntity<String> response = null;
        int statusCode = -1;
        String responseBody = null;
        HttpHeaders headers = null;
        try {
            response = restTemplate.exchange(url, method, request, String.class);
            //statusCode = response.getStatusCodeValue();
            //responseBody = response.getBody();
            //headers = response.getHeaders();
        } catch (HttpStatusCodeException statusCodeException) {
            response = new ResponseEntity<String>(statusCodeException.getResponseHeaders(), statusCodeException.getStatusCode());
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }

        return response;
    }

    public OAuth2ProtectedResourceDetails resourceDetails(Auth auth) {

        BaseOAuth2ProtectedResourceDetails details1 = null;
        if (auth.getGrantType() == GrantType.authorization_code) {
            return getAbstractRedirectResourceDetails(auth);
        } else if (auth.getGrantType() == GrantType.implicit) {
            return getAbstractRedirectResourceDetails(auth);
        } else if (auth.getGrantType() == GrantType.client_credentials) {
            return getBaseOAuth2ProtectedResourceDetails(auth);
        } else if (auth.getGrantType() == GrantType.password) {
            return getResourceOwnerPasswordResourceDetails(auth);
        } else {
            logger.warn("Invalid grant-type [{}]", auth.getGrantType());
        }

        return null;
    }


    public OAuth2ProtectedResourceDetails getResourceOwnerPasswordResourceDetails(Auth auth) {

        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();


        details.setId(auth.getId());
        details.setClientId(auth.getUsername());
        details.setClientSecret(auth.getPassword());

        //details.setUsername(auth.getUsername());
        //details.setPassword(auth.getPassword());

        details.setAccessTokenUri(auth.getAccessTokenUri());

        details.setTokenName(auth.getTokenName());
        if (StringUtils.isNotEmpty(auth.getScope())) {
            details.setScope(Arrays.asList(auth.getScope().split(",")));
        }

        if (auth.getClientAuthenticationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getClientAuthenticationScheme().toString()));
        }

        if (auth.getAuthorizationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getAuthorizationScheme().toString()));
        }

        //details.setUserAuthorizationUri(auth.getUserAuthorizationUri());
        //details.setPreEstablishedRedirectUri(auth.getPreEstablishedRedirectUri());

        //details.setUseCurrentUri(false);

        return details;
    }

    public OAuth2ProtectedResourceDetails getBaseOAuth2ProtectedResourceDetails(Auth auth) {

        BaseOAuth2ProtectedResourceDetails details = new BaseOAuth2ProtectedResourceDetails();


        details.setId(auth.getId());
        details.setClientId(auth.getUsername());
        details.setClientSecret(auth.getPassword());

        details.setAccessTokenUri(auth.getAccessTokenUri());

        details.setTokenName(auth.getTokenName());
        if (StringUtils.isNotEmpty(auth.getScope())) {
            details.setScope(Arrays.asList(auth.getScope().split(",")));
        }

        if (auth.getClientAuthenticationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getClientAuthenticationScheme().toString()));
        }

        if (auth.getAuthorizationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getAuthorizationScheme().toString()));
        }

        //details.setUserAuthorizationUri(auth.getUserAuthorizationUri());
        //details.setPreEstablishedRedirectUri(auth.getPreEstablishedRedirectUri());

        //details.setUseCurrentUri(false);

        return details;
    }

    public OAuth2ProtectedResourceDetails getAbstractRedirectResourceDetails(Auth auth) {

        AbstractRedirectResourceDetails details = new ImplicitResourceDetails();


        details.setId(auth.getId());
        details.setClientId(auth.getUsername());
        details.setClientSecret(auth.getPassword());

        details.setAccessTokenUri(auth.getAccessTokenUri());

        details.setTokenName(auth.getTokenName());
        if (StringUtils.isNotEmpty(auth.getScope())) {
            details.setScope(Arrays.asList(auth.getScope().split(",")));
        }

        if (auth.getClientAuthenticationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getClientAuthenticationScheme().toString()));
        }

        if (auth.getAuthorizationScheme() != null) {
            details.setClientAuthenticationScheme(org.springframework.security.oauth2.common.AuthenticationScheme.valueOf(auth.getAuthorizationScheme().toString()));
        }

        details.setUserAuthorizationUri(auth.getUserAuthorizationUri());
        details.setPreEstablishedRedirectUri(auth.getPreEstablishedRedirectUri());

        details.setUseCurrentUri(false);

        return details;
    }

    private ResponseEntity<String> execBasicRequest(String url, HttpMethod method, HttpHeaders httpHeaders, String req, Auth auth) {
        // execute request
        RestTemplate restTemplate = new RestTemplate();

        if (auth != null && (auth.getAuthType() == AuthType.BasicAuth || auth.getAuthType() == AuthType.BASIC)) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(auth.getUsername(), auth.getPassword()));
        }

        //logger.info("Request: [{}]", req);
        HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

        ResponseEntity<String> response = null;
        int statusCode = -1;
        String responseBody = null;
        HttpHeaders headers = null;
        try {
            response = restTemplate.exchange(url, method, request, String.class);
            //statusCode = response.getStatusCodeValue();
            //responseBody = response.getBody();
            //headers = response.getHeaders();
        } catch (HttpStatusCodeException statusCodeException) {
            response = new ResponseEntity<String>(statusCodeException.getResponseHeaders(), statusCodeException.getStatusCode());
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }

        return response;
    }
}
